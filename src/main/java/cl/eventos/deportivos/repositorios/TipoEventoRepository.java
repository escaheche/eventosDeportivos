package cl.eventos.deportivos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.eventos.deportivos.modelos.TipoEvento;

public interface TipoEventoRepository extends JpaRepository<TipoEvento, Long>{

}
