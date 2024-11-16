package cl.eventos.deportivos.repositorios;

import cl.eventos.deportivos.modelos.Role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Optional<Role> findByName(String name); // Método para buscar un rol por su nombre
   
}
