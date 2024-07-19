package cl.eventos.deportivos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.eventos.deportivos.modelos.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {

}
