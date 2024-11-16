package cl.eventos.deportivos.servicios;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.eventos.deportivos.dto.CustomUserDetails;
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

    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario); // Guarda el usuario en la base de datos
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
    public Usuario obtenerPorCorreoElectronico(String correoElectronico) {
        return usuarioRepository.findByCorreoElectronico(correoElectronico)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo: " + correoElectronico));
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
        // Usar Optional para manejar la posibilidad de que no se encuentre un usuario
        Usuario usuario = usuarioRepository.findByCorreoElectronico(correoElectronico)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo: " + correoElectronico));

        return new CustomUserDetails(usuario);
    }

    public boolean existeCorreo(String correoElectronico) {
        return usuarioRepository.findByCorreoElectronico(correoElectronico).isPresent();
    }

    


}

