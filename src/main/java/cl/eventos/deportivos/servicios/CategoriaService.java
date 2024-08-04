package cl.eventos.deportivos.servicios;

import cl.eventos.deportivos.modelos.Categoria;
import cl.eventos.deportivos.modelos.SubtipoEvento;
import cl.eventos.deportivos.modelos.Usuario;
import cl.eventos.deportivos.repositorios.CategoriaRepository;
import cl.eventos.deportivos.repositorios.SubtipoEventoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private SubtipoEventoRepository subtipoEventoRepository;

    public Categoria asignarCategoriaPorFechaNacimientoYDistancia(Usuario usuario, double distancia, LocalDate fechaCompetencia) {
        int edad = calcularEdad(usuario.getFechaNacimiento(), fechaCompetencia);
        return categoriaRepository.findByEdadMinimaLessThanEqualAndEdadMaximaGreaterThanEqualAndDistancia(edad, edad, distancia);
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
        // Cargar el SubtipoEvento completo desde la base de datos usando el ID
        SubtipoEvento subtipoEvento = subtipoEventoRepository.findById(categoria.getSubtipoEvento().getId())
                .orElseThrow(() -> new RuntimeException("SubtipoEvento no encontrado"));

        // Asociar el SubtipoEvento cargado completamente a la Categoria
        categoria.setSubtipoEvento(subtipoEvento);

        return categoriaRepository.save(categoria);
    }

    public Categoria actualizarCategoria(Long id, Categoria categoria) {
        Categoria categoriaExistente = obtenerCategoriaPorId(id);
        categoriaExistente.setNombre(categoria.getNombre());
        // Actualiza otros campos si es necesario
        return categoriaRepository.save(categoriaExistente);
    }

    public void eliminarCategoria(Long id) {
        Categoria categoria = obtenerCategoriaPorId(id);
        categoriaRepository.delete(categoria);
    }
}


