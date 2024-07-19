package cl.eventos.deportivos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import cl.eventos.deportivos.modelos.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	 User findByCorreoElectronico(String correoElectronico);

}
