package cl.eventos.deportivos.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import cl.eventos.deportivos.componentes.JwtUtils;
import cl.eventos.deportivos.modelos.Usuario;
import cl.eventos.deportivos.servicios.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import cl.eventos.deportivos.dto.AuthRequest;
import cl.eventos.deportivos.dto.CustomUserDetails;

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

    @PostMapping("/generatetoken")
    public Map<String, Object> generateToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Cargar el CustomUserDetails
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(authRequest.getUsername());
        Usuario usuario = userDetails.getUsuario();

        // Generar token con detalles adicionales del usuario
        String token = jwtUtils.generateToken(usuario.getCorreoElectronico(), usuario.getRole().getName(), usuario.getNombre(), usuario.getApellido());

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
}


