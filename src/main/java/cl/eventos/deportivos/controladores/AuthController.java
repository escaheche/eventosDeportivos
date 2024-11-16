package cl.eventos.deportivos.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import cl.eventos.deportivos.componentes.JwtUtils;
import cl.eventos.deportivos.modelos.Role;
import cl.eventos.deportivos.modelos.Usuario;
import cl.eventos.deportivos.servicios.RoleService;
import cl.eventos.deportivos.servicios.TokenService;
import cl.eventos.deportivos.servicios.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import cl.eventos.deportivos.dto.AuthRequest;
import cl.eventos.deportivos.dto.CustomUserDetails;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private TokenService tokenService;

	// Inyectar UsuarioService
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/generatetoken")
	public Map<String, Object> generateToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Cargar el CustomUserDetails
		CustomUserDetails userDetails = (CustomUserDetails) userDetailsService
				.loadUserByUsername(authRequest.getUsername());
		Usuario usuario = userDetails.getUsuario();

		// Generar token con detalles adicionales del usuario
		String token = jwtUtils.generateToken(usuario.getCorreoElectronico(), usuario.getRole().getName(),
				usuario.getNombre(), usuario.getApellido());

		// Crear respuesta
		Map<String, Object> response = new HashMap<>();
		response.put("token", token);
		response.put("role", usuario.getRole().getName());
		response.put("nombre", usuario.getNombre());
		response.put("apellido", usuario.getApellido());

		return response;
	}

	@PostMapping("/refreshtoken")
	public Map<String, Object> refreshToken(@RequestBody Map<String, String> request) {
		String token = request.get("token");
		String newToken = jwtUtils.refreshToken(token);

		// Obtener el nombre de usuario del nuevo token
		String username = jwtUtils.getUsernameFromToken(newToken);

		// Buscar el usuario en el sistema usando usuarioService
		Usuario usuario = usuarioService.obtenerPorCorreoElectronico(username);

		// Revocar el token anterior y guardar el nuevo token
		tokenService.revokeToken(token); // Revocar el anterior
		tokenService.saveTokenForUser(newToken, usuario); // Guardar el nuevo token

		// Crear la respuesta con el nuevo token
		Map<String, Object> response = new HashMap<>();
		response.put("token", newToken);
		return response;
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request) {
		String token = extractTokenFromRequest(request);
		if (token != null) {
			tokenService.revokeToken(token);
			return ResponseEntity.ok("Logout successful, token invalidated");
		} else {
			return ResponseEntity.badRequest().body("Invalid token");
		}
	}

	private String extractTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	// Registrar usuario con rol fijo "Usuario"
	@PostMapping("/register")
	public ResponseEntity<?> registrarUsuario(@RequestBody Map<String, String> userDetails) {
		String nombre = userDetails.get("nombre");
		String apellido = userDetails.get("apellido");
		String correo = userDetails.get("correo");
		String rut = userDetails.get("rut");
		String fechaNacimiento = userDetails.get("fechaNacimiento");
		String contrasena = userDetails.get("contrasena");

		// Validar si el correo ya está registrado
		if (usuarioService.existeCorreo(correo)) {
			return ResponseEntity.badRequest().body("Correo ya registrado.");
		}

		// Convertir la fecha de nacimiento de String a LocalDate
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formatter);

		// Crear el nuevo usuario con el rol fijo de "Usuario"
		Usuario nuevoUsuario = new Usuario();
		nuevoUsuario.setNombre(nombre);
		nuevoUsuario.setApellido(apellido);
		nuevoUsuario.setCorreoElectronico(correo);
		nuevoUsuario.setRut(rut);
		nuevoUsuario.setFechaNacimiento(fechaNac); // Establecer la fecha de nacimiento
		nuevoUsuario.setContrasena(passwordEncoder.encode(contrasena));

		// Asignar siempre el rol de "Usuario"
		Role rolUsuario = roleService.obtenerRolPorNombre("Usuario");
		nuevoUsuario.setRole(rolUsuario);

		// Guardar el nuevo usuario en la base de datos
		usuarioService.guardarUsuario(nuevoUsuario);

		// Respuesta con datos del usuario creado
		Map<String, String> response = new HashMap<>();
		response.put("mensaje", "Usuario creado exitosamente.");
		response.put("nombre", nuevoUsuario.getNombre());
		response.put("correo", nuevoUsuario.getCorreoElectronico());

		return ResponseEntity.ok(response);
	}
}
