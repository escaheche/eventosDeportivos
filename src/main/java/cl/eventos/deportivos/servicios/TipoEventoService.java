package cl.eventos.deportivos.servicios;

import cl.eventos.deportivos.modelos.TipoEvento;
import cl.eventos.deportivos.repositorios.TipoEventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoEventoService {

    @Autowired
    private TipoEventoRepository tipoEventoRepository;

    public List<TipoEvento> obtenerTodosLosTiposDeEvento() {
        return tipoEventoRepository.findAll();
    }

    public TipoEvento obtenerTipoEventoPorId(Long id) {
        return tipoEventoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tipo de Evento no encontrado"));
    }

    public TipoEvento crearTipoEvento(TipoEvento tipoEvento) {
        return tipoEventoRepository.save(tipoEvento);
    }

    public TipoEvento actualizarTipoEvento(Long id, TipoEvento tipoEvento) {
        TipoEvento tipoEventoExistente = obtenerTipoEventoPorId(id);
        tipoEventoExistente.setNombre(tipoEvento.getNombre());
        tipoEventoExistente.setDescripcion(tipoEvento.getDescripcion());
        return tipoEventoRepository.save(tipoEventoExistente);
    }

    public void eliminarTipoEvento(Long id) {
        TipoEvento tipoEvento = obtenerTipoEventoPorId(id);
        tipoEventoRepository.delete(tipoEvento);
    }
}

