package cl.eventos.deportivos.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.eventos.deportivos.modelos.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	 Optional<Usuario> findByCorreoElectronico(String correoElectronico);
}
