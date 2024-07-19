package cl.eventos.deportivos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.eventos.deportivos.modelos.Inscripcion;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long>{

}
