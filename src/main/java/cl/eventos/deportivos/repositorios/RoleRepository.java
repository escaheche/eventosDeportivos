package cl.eventos.deportivos.repositorios;

import cl.eventos.deportivos.modelos.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
   
}
