package cl.eventos.deportivos;

import cl.eventos.deportivos.controladores.UsuarioController;
import cl.eventos.deportivos.dto.UsuarioDTO;
import cl.eventos.deportivos.modelos.Role;
import cl.eventos.deportivos.modelos.Usuario;
import cl.eventos.deportivos.servicios.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class UsuarioControllerTest {
	
	  @Mock
	    private UsuarioService usuarioService;

	    @InjectMocks
	    private UsuarioController usuarioController;

	    private Usuario usuario;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	        usuario = new Usuario();
	        usuario.setId(1L);
	        usuario.setNombre("Juan");
	        usuario.setApellido("Pérez");
	        usuario.setCorreoElectronico("juan.perez@example.com");
	        usuario.setContrasena("password123");
	        usuario.setRut("12345678-9");
	        usuario.setFechaNacimiento(LocalDate.of(1990, 1, 1));
	        
	     // Inicializar el Role
	        Role role = new Role();
	        role.setId(1L);
	        role.setName("Admin");
	        usuario.setRole(role);
	    }

	    @Test
	    void testObtenerTodosLosUsuarios() {
	        when(usuarioService.obtenerTodosLosUsuarios()).thenReturn(Arrays.asList(usuario));

	        ResponseEntity<List<UsuarioDTO>> response = usuarioController.obtenerTodosLosUsuarios();

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals(1, response.getBody().size());
	        assertEquals("Juan", response.getBody().get(0).getNombre());
	        assertEquals("Admin", response.getBody().get(0).getRol());
	        verify(usuarioService, times(1)).obtenerTodosLosUsuarios();
	    }

	    @Test
	    void testObtenerUsuarioPorId() {
	        when(usuarioService.obtenerUsuarioPorId(anyLong())).thenReturn(usuario);

	        ResponseEntity<Usuario> response = usuarioController.obtenerUsuarioPorId(1L);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals("Juan", response.getBody().getNombre());
	        verify(usuarioService, times(1)).obtenerUsuarioPorId(anyLong());
	    }

	    @Test
	    void testCrearUsuario() {
	        Map<String, Object> payload = Map.of(
	            "nombre", "Juan",
	            "apellido", "Pérez",
	            "correoElectronico", "juan.perez@example.com",
	            "contrasena", "password123",
	            "rut", "12345678-9",
	            "fechaNacimiento", "1990-01-01",
	            "roleId", 1L
	        );

	        when(usuarioService.crearUsuario(any(Usuario.class), anyLong())).thenReturn(usuario);

	        ResponseEntity<Usuario> response = usuarioController.crearUsuario(payload);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals("Juan", response.getBody().getNombre());
	        verify(usuarioService, times(1)).crearUsuario(any(Usuario.class), anyLong());
	    }

	    @Test
	    void testCrearUsuarioSinRoleId() {
	        Map<String, Object> payload = Map.of(
	            "nombre", "Juan",
	            "apellido", "Pérez",
	            "correoElectronico", "juan.perez@example.com",
	            "contrasena", "password123",
	            "rut", "12345678-9",
	            "fechaNacimiento", "1990-01-01"
	        );

	        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
	            usuarioController.crearUsuario(payload);
	        });

	        assertEquals("El campo 'roleId' es obligatorio", exception.getMessage());
	        verify(usuarioService, times(0)).crearUsuario(any(Usuario.class), anyLong());
	    }

	    @Test
	    void testActualizarUsuario() {
	        when(usuarioService.actualizarUsuario(anyLong(), any(Usuario.class))).thenReturn(usuario);

	        ResponseEntity<Usuario> response = usuarioController.actualizarUsuario(1L, usuario);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals("Juan", response.getBody().getNombre());
	        verify(usuarioService, times(1)).actualizarUsuario(anyLong(), any(Usuario.class));
	    }

	    @Test
	    void testEliminarUsuario() {
	        doNothing().when(usuarioService).eliminarUsuario(anyLong());

	        ResponseEntity<Void> response = usuarioController.eliminarUsuario(1L);

	        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	        verify(usuarioService, times(1)).eliminarUsuario(anyLong());
	    }

}
