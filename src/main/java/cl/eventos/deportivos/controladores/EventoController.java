package cl.eventos.deportivos.controladores;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.eventos.deportivos.modelos.Evento;
import cl.eventos.deportivos.servicios.EventoService;
import cl.eventos.deportivos.servicios.GeocodingService;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private GeocodingService geocodingService;

    @GetMapping
    public List<Evento> listarTodos() {
        return eventoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> obtenerPorId(@PathVariable Long id) {
        Evento evento = eventoService.obtenerPorId(id);
        return evento != null ? ResponseEntity.ok(evento) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Evento> crear(@RequestBody Evento evento) {
        Map<String, Double> coordinates = geocodingService.getLatLongFromAddress(evento.getDireccion());
        evento.setLatitud(coordinates.get("lat"));
        evento.setLongitud(coordinates.get("lng"));
        Evento nuevoEvento = eventoService.guardar(evento);
        return ResponseEntity.ok(nuevoEvento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> actualizar(@PathVariable Long id, @RequestBody Evento evento) {
        Evento eventoExistente = eventoService.obtenerPorId(id);
        if (eventoExistente == null) {
            return ResponseEntity.notFound().build();
        }
        Map<String, Double> coordinates = geocodingService.getLatLongFromAddress(evento.getDireccion());
        evento.setLatitud(coordinates.get("lat"));
        evento.setLongitud(coordinates.get("lng"));
        evento.setId(id);
        evento.setFechaCreacion(eventoExistente.getFechaCreacion());  // Mantener la fecha de creación original
        Evento eventoActualizado = eventoService.guardar(evento);
        return ResponseEntity.ok(eventoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (eventoService.obtenerPorId(id) == null) {
            return ResponseEntity.notFound().build();
        }
        eventoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

