package cl.eventos.deportivos.servicios;

import cl.eventos.deportivos.modelos.SubtipoEvento;
import cl.eventos.deportivos.modelos.TipoEvento;
import cl.eventos.deportivos.repositorios.SubtipoEventoRepository;
import cl.eventos.deportivos.repositorios.TipoEventoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubtipoEventoService {

    @Autowired
    private SubtipoEventoRepository subtipoEventoRepository;
    
    @Autowired
    private TipoEventoRepository tipoEventoRepository;


    public List<SubtipoEvento> obtenerTodosLosSubtiposDeEvento() {
        return subtipoEventoRepository.findAll();
    }

    public SubtipoEvento obtenerSubtipoEventoPorId(Long id) {
        return subtipoEventoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Subtipo de Evento no encontrado"));
    }

    public SubtipoEvento crearSubtipoEvento(SubtipoEvento subtipoEvento) {
        // Cargar el TipoEvento completo desde la base de datos usando el ID
        TipoEvento tipoEvento = tipoEventoRepository.findById(subtipoEvento.getTipoEvento().getId())
                .orElseThrow(() -> new RuntimeException("TipoEvento no encontrado"));

        // Asociar el TipoEvento cargado completamente al SubtipoEvento
        subtipoEvento.setTipoEvento(tipoEvento);

        return subtipoEventoRepository.save(subtipoEvento);
    }

    public SubtipoEvento actualizarSubtipoEvento(Long id, SubtipoEvento subtipoEvento) {
        SubtipoEvento subtipoEventoExistente = obtenerSubtipoEventoPorId(id);
        subtipoEventoExistente.setNombre(subtipoEvento.getNombre());
        subtipoEventoExistente.setDescripcion(subtipoEvento.getDescripcion());
        subtipoEventoExistente.setTipoEvento(subtipoEvento.getTipoEvento());
        return subtipoEventoRepository.save(subtipoEventoExistente);
    }

    public void eliminarSubtipoEvento(Long id) {
        SubtipoEvento subtipoEvento = obtenerSubtipoEventoPorId(id);
        subtipoEventoRepository.delete(subtipoEvento);
    }
}
