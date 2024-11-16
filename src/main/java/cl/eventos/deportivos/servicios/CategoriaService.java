package cl.eventos.deportivos.servicios;

import cl.eventos.deportivos.modelos.Categoria;
import cl.eventos.deportivos.modelos.Usuario;
import cl.eventos.deportivos.repositorios.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria asignarCategoriaPorFechaNacimientoYDistancia(Usuario usuario, LocalDate fechaCompetencia) {
        int edad = calcularEdad(usuario.getFechaNacimiento(), fechaCompetencia);
        return categoriaRepository.findByEdadMinimaLessThanEqualAndEdadMaximaGreaterThanEqual(edad, edad);
    }

    private int calcularEdad(LocalDate fechaNacimiento, LocalDate fechaCompetencia) {
        return Period.between(fechaNacimiento, fechaCompetencia).getYears();
    }
    
    public List<Categoria> obtenerTodasLasCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria obtenerCategoriaPorId(Long id) {
        return categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    public Categoria crearCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria actualizarCategoria(Long id, Categoria categoria) {
        Categoria categoriaExistente = obtenerCategoriaPorId(id);
        categoriaExistente.setNombre(categoria.getNombre());
        categoriaExistente.setEdadMinima(categoria.getEdadMinima());
        categoriaExistente.setEdadMaxima(categoria.getEdadMaxima());
        return categoriaRepository.save(categoriaExistente);
    }

    public void eliminarCategoria(Long id) {
        Categoria categoria = obtenerCategoriaPorId(id);
        categoriaRepository.delete(categoria);
    }
}



