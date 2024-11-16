package cl.eventos.deportivos.servicios;

import cl.eventos.deportivos.modelos.Categoria;
import cl.eventos.deportivos.modelos.SubtipoEvento;
import cl.eventos.deportivos.modelos.TipoEvento;
import cl.eventos.deportivos.repositorios.CategoriaRepository;
import cl.eventos.deportivos.repositorios.SubtipoEventoRepository;
import cl.eventos.deportivos.repositorios.TipoEventoRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SubtipoEventoService {

    @Autowired
    private SubtipoEventoRepository subtipoEventoRepository;

    @Autowired
    private TipoEventoRepository tipoEventoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<SubtipoEvento> obtenerTodosLosSubtiposDeEvento() {
        return subtipoEventoRepository.findAll();
    }

    public SubtipoEvento obtenerSubtipoEventoPorId(Long id) {
        return subtipoEventoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Subtipo de Evento no encontrado"));
    }

    public SubtipoEvento crearSubtipoEvento(SubtipoEvento subtipoEvento, Long tipoEventoId, List<Long> categoriaIds) {
        // Buscar el TipoEvento en base al ID proporcionado
        TipoEvento tipoEvento = tipoEventoRepository.findById(tipoEventoId)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de evento no encontrado"));

        // Asignar el TipoEvento al SubtipoEvento
        subtipoEvento.setTipoEvento(tipoEvento);

        // Asignar las categorías, si los IDs de las categorías están presentes
        if (categoriaIds != null && !categoriaIds.isEmpty()) {
            List<Categoria> categorias = categoriaRepository.findAllById(categoriaIds);
            subtipoEvento.setCategorias(categorias);  // Aquí asignamos las categorías correctamente
        }

        // Guardar y retornar el nuevo subtipo de evento
        return subtipoEventoRepository.save(subtipoEvento);
    }





    public SubtipoEvento actualizarSubtipoEvento(Long id, SubtipoEvento subtipoEvento, List<Long> categoriaIds) {
        SubtipoEvento subtipoEventoExistente = obtenerSubtipoEventoPorId(id);
        subtipoEventoExistente.setNombre(subtipoEvento.getNombre());
        subtipoEventoExistente.setDescripcion(subtipoEvento.getDescripcion());

        // Actualizar el TipoEvento asociado
        TipoEvento tipoEvento = tipoEventoRepository.findById(subtipoEvento.getTipoEvento().getId())
                .orElseThrow(() -> new RuntimeException("TipoEvento no encontrado"));
        subtipoEventoExistente.setTipoEvento(tipoEvento);

        // Asignar categorías si se proporcionaron
        if (categoriaIds != null && !categoriaIds.isEmpty()) {
            List<Categoria> categorias = categoriaRepository.findAllById(categoriaIds);
            subtipoEventoExistente.setCategorias(categorias);
        }

        return subtipoEventoRepository.save(subtipoEventoExistente);
    }

    public void eliminarSubtipoEvento(Long id) {
        SubtipoEvento subtipoEvento = obtenerSubtipoEventoPorId(id);
        subtipoEventoRepository.delete(subtipoEvento);
    }
}

