package cl.eventos.deportivos.controladores;

import cl.eventos.deportivos.dto.UsuarioDTO;
import cl.eventos.deportivos.modelos.Usuario;
import cl.eventos.deportivos.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    
   
    @GetMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<List<UsuarioDTO>> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        List<UsuarioDTO> usuariosDTO = usuarios.stream()
            .map(usuario -> new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getCorreoElectronico(),
                usuario.getRut(),
                usuario.getFechaNacimiento(),
                usuario.getRole().getName(), 
                usuario.getCategoria() != null ? usuario.getCategoria().getNombre() : "Sin categoría"))
            .collect(Collectors.toList());
        return ResponseEntity.ok(usuariosDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Map<String, Object> payload) {
        if (!payload.containsKey("roleId")) {
            throw new RuntimeException("El campo 'roleId' es obligatorio");
        }

        Long roleId = Long.valueOf(payload.get("roleId").toString());

        Usuario usuario = new Usuario();
        usuario.setNombre((String) payload.get("nombre"));
        usuario.setApellido((String) payload.get("apellido"));
        usuario.setCorreoElectronico((String) payload.get("correoElectronico"));
        usuario.setContrasena((String) payload.get("contrasena"));
        usuario.setRut((String) payload.get("rut"));
        usuario.setFechaNacimiento(LocalDate.parse((String) payload.get("fechaNacimiento")));

        Usuario nuevoUsuario = usuarioService.crearUsuario(usuario, roleId);
        return ResponseEntity.ok(nuevoUsuario);
    }

    
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, usuario);
        return ResponseEntity.ok(usuarioActualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/me")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> obtenerUsuarioActual() {
        // Obtener el contexto de autenticación actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("No estás autenticado");
        }

        // Obtener el nombre de usuario del usuario autenticado
        String correoElectronico = authentication.getName();

        // Buscar el usuario en el sistema usando el correo electrónico
        Usuario usuario = usuarioService.obtenerPorCorreoElectronico(correoElectronico);

        // Crear un mapa de respuesta con los detalles del usuario
        Map<String, Object> response = new HashMap<>();
        response.put("id", usuario.getId());
        response.put("nombre", usuario.getNombre());
        response.put("apellido", usuario.getApellido());
        response.put("correo", usuario.getCorreoElectronico());
        response.put("role", usuario.getRole().getName());

        return ResponseEntity.ok(response);
    }

}

