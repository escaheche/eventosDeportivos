package cl.eventos.deportivos.servicios;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.eventos.deportivos.modelos.Role;
import cl.eventos.deportivos.modelos.Usuario;
import cl.eventos.deportivos.repositorios.RoleRepository;
import cl.eventos.deportivos.repositorios.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService implements UserDetailsService{

	private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario crearUsuario(Usuario usuario, Long roleId) {
        // Buscar el rol por ID y asignarlo al usuario
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        usuario.setRole(role);
        
        // Encriptar la contraseña antes de guardarla
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        
        return usuarioRepository.save(usuario);
    }
    // Buscar un usuario por ID
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // Obtener todos los usuarios
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    // Eliminar un usuario por ID
    public void eliminarUsuario(Long id) {
        Usuario usuario = obtenerUsuarioPorId(id);
        usuarioRepository.delete(usuario);
    }

 // Actualizar un usuario existente
    public Usuario actualizarUsuario(Long id, Usuario usuario) {
        Usuario usuarioExistente = obtenerUsuarioPorId(id);
        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setApellido(usuario.getApellido());
        usuarioExistente.setCorreoElectronico(usuario.getCorreoElectronico());
        if (usuario.getContrasena() != null) {
            usuarioExistente.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        }
        usuarioExistente.setFechaNacimiento(usuario.getFechaNacimiento());
        usuarioExistente.setRole(usuario.getRole());
        return usuarioRepository.save(usuarioExistente);
    }
    
    @Override
    public UserDetails loadUserByUsername(String correoElectronico) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreoElectronico(correoElectronico);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return new org.springframework.security.core.userdetails.User(
            usuario.getCorreoElectronico(), 
            usuario.getContrasena(), 
            new ArrayList<>()
        );
    }

}

