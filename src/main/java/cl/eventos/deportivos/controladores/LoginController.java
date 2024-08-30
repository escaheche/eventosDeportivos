package cl.eventos.deportivos.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import cl.eventos.deportivos.componentes.JwtUtils;
import cl.eventos.deportivos.dto.AuthRequest;
import cl.eventos.deportivos.modelos.Usuario;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(@RequestBody AuthRequest authRequest) {
        // Autenticar al usuario
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Cargar los detalles del usuario
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        Usuario usuario = (Usuario) userDetails;  // Asumiendo que Usuario implementa UserDetails

        // Generar el token JWT con rol, nombre y apellido
        String token = jwtUtils.generateToken(usuario.getUsername(), usuario.getRole().getName(), usuario.getNombre(), usuario.getApellido());

        // Crear la respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("role", usuario.getRole().getName());
        response.put("nombre", usuario.getNombre());
        response.put("apellido", usuario.getApellido());

        return response;
    }

    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            model.addAttribute("name", principal.getAttribute("name"));
        }
        return "home";
    }
}
