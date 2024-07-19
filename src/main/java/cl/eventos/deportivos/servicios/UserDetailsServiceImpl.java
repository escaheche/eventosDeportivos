package cl.eventos.deportivos.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cl.eventos.deportivos.repositorios.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
    private UsuarioRepository usuarioRepository;

	 @Override
	    public UserDetails loadUserByUsername(String correoElectronico) throws UsernameNotFoundException {
		 User user = usuarioRepository.findByCorreoElectronico(correoElectronico);
	        if (user == null) {
	            throw new UsernameNotFoundException("User not found");
	        }
	        return user;
	    }

}
