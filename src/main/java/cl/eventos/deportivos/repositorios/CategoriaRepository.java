package cl.eventos.deportivos.repositorios;

import cl.eventos.deportivos.modelos.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	 Categoria findByEdadMinimaLessThanEqualAndEdadMaximaGreaterThanEqualAndDistancia(int edadMinima, int edadMaxima, double distancia);
}

