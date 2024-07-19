package cl.eventos.deportivos.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.eventos.deportivos.modelos.Inscripcion;
import cl.eventos.deportivos.repositorios.InscripcionRepository;

@Service
public class InscripcionService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    public List<Inscripcion> listarTodos() {
        return inscripcionRepository.findAll();
    }

    public Inscripcion obtenerPorId(Long id) {
        return inscripcionRepository.findById(id).orElse(null);
    }

    public Inscripcion guardar(Inscripcion inscripcion) {
        return inscripcionRepository.save(inscripcion);
    }

    public void eliminar(Long id) {
        inscripcionRepository.deleteById(id);
    }
}
