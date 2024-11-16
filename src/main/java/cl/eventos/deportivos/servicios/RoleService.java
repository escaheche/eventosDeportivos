package cl.eventos.deportivos.servicios;

import cl.eventos.deportivos.modelos.Role;
import cl.eventos.deportivos.repositorios.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> obtenerTodosLosRoles() {
        return roleRepository.findAll();
    }

    public Role obtenerRolPorId(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    }

    public Role crearRol(Role role) {
        return roleRepository.save(role);
    }

    public Role actualizarRol(Long id, Role role) {
        Role rolExistente = obtenerRolPorId(id);
        rolExistente.setName(role.getName());
        return roleRepository.save(rolExistente);
    }

    public void eliminarRol(Long id) {
        Role rol = obtenerRolPorId(id);
        roleRepository.delete(rol);
    }
    
    // Nuevo método para obtener el rol por nombre
    public Role obtenerRolPorNombre(String nombre) {
        return roleRepository.findByName(nombre)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con el nombre: " + nombre));
    }
}
