package cl.eventos.deportivos.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.eventos.deportivos.modelos.Evento;
import cl.eventos.deportivos.repositorios.EventoRepository;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }

    public Evento obtenerPorId(Long id) {
        return eventoRepository.findById(id).orElse(null);
    }

    public Evento guardar(Evento evento) {
        return eventoRepository.save(evento);
    }

    public void eliminar(Long id) {
        eventoRepository.deleteById(id);
    }
}
