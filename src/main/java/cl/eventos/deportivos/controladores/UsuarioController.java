package cl.eventos.deportivos.controladores;

import cl.eventos.deportivos.modelos.Usuario;
import cl.eventos.deportivos.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    
   
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
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
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, usuario);
        return ResponseEntity.ok(usuarioActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}

