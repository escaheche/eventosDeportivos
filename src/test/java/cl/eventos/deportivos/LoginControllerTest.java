package cl.eventos.deportivos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import cl.eventos.deportivos.componentes.JwtUtils;
import cl.eventos.deportivos.controladores.LoginController;
import cl.eventos.deportivos.dto.AuthRequest;

@SpringBootTest
public class LoginControllerTest {
	
	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private UserDetailsService userDetailsService;

	@Mock
	private JwtUtils jwtUtils;

	@Mock
	private Authentication authentication;

	@Mock
	private UserDetails userDetails;

	@Mock
	private SecurityContext securityContext;

	@InjectMocks
	private LoginController loginController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void contextLoads() {
		// Este test verifica que el contexto de Spring Boot se carga correctamente
	}

	@Test
	void testLogin() {
		// Arrange
	    AuthRequest authRequest = new AuthRequest();
	    authRequest.setUsername("testuser");
	    authRequest.setPassword("testpass");

	    // Simula un UserDetails con el nombre de usuario esperado
	    UserDetails userDetails = org.springframework.security.core.userdetails.User
	        .withUsername("testuser")
	        .password("testpass")
	        .authorities("ROLE_USER")
	        .build();

	    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
	            .thenReturn(authentication);
	    when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);
	    when(jwtUtils.generateToken("testuser")).thenReturn("mockedToken");
	    when(securityContext.getAuthentication()).thenReturn(authentication);
	    SecurityContextHolder.setContext(securityContext);

	    // Act
	    Map<String, Object> response = loginController.login(authRequest);

	    // Assert
	    verify(authenticationManager, times(1))
	            .authenticate(any(UsernamePasswordAuthenticationToken.class));
	    verify(userDetailsService, times(1))
	            .loadUserByUsername("testuser");
	    verify(jwtUtils, times(1))
	            .generateToken("testuser");

	    assertEquals("mockedToken", response.get("token"));
	}
}
