package cl.eventos.deportivos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.eventos.deportivos.modelos.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
