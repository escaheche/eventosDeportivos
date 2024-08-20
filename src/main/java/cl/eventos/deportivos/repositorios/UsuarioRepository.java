package cl.eventos.deportivos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.eventos.deportivos.modelos.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	 Usuario findByCorreoElectronico(String correoElectronico);

}
