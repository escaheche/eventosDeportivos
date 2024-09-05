package cl.eventos.deportivos.controladores;

import cl.eventos.deportivos.modelos.Role;
import cl.eventos.deportivos.servicios.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<List<Role>> obtenerTodosLosRoles() {
        List<Role> roles = roleService.obtenerTodosLosRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<Role> obtenerRolPorId(@PathVariable Long id) {
        Role role = roleService.obtenerRolPorId(id);
        return ResponseEntity.ok(role);
    }
    
    
    @PostMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<Role> crearRol(@RequestBody Role role) {
        Role nuevoRol = roleService.crearRol(role);
        return ResponseEntity.ok(nuevoRol);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<Role> actualizarRol(@PathVariable Long id, @RequestBody Role role) {
        Role rolActualizado = roleService.actualizarRol(id, role);
        return ResponseEntity.ok(rolActualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<Void> eliminarRol(@PathVariable Long id) {
        roleService.eliminarRol(id);
        return ResponseEntity.noContent().build();
    }
}

