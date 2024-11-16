package cl.eventos.deportivos.controladores;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import cl.eventos.deportivos.dto.EventoDTO;
import cl.eventos.deportivos.modelos.Evento;
import cl.eventos.deportivos.servicios.EventoService;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public List<EventoDTO> listarTodos() {
        List<Evento> eventos = eventoService.listarTodos();

        // Convertir la entidad Evento a EventoDTO
        return eventos.stream().map(evento -> {
            String tipoEventoNombre = evento.getTipoEvento() != null ? evento.getTipoEvento().getNombre() : null;
            String subtipoEventoNombre = evento.getSubtipoEvento() != null ? evento.getSubtipoEvento().getNombre() : null;
            String categoriaNombre = evento.getCategoria() != null ? evento.getCategoria().getNombre() : null;

            return new EventoDTO(
                evento.getId(),
                evento.getNombre(),
                evento.getDescripcion(),
                evento.getDireccion(),
                evento.getFecha(),
                evento.getFechaCreacion(),
                tipoEventoNombre,
                subtipoEventoNombre,
                categoriaNombre
            );
        }).collect(Collectors.toList());
    }

    // Cualquier persona puede obtener un evento por su ID
    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> obtenerPorId(@PathVariable Long id) {
        Evento evento = eventoService.obtenerPorId(id);

        if (evento == null) {
            return ResponseEntity.notFound().build();
        }

        String tipoEventoNombre = evento.getTipoEvento() != null ? evento.getTipoEvento().getNombre() : null;
        String subtipoEventoNombre = evento.getSubtipoEvento() != null ? evento.getSubtipoEvento().getNombre() : null;
        String categoriaNombre = evento.getCategoria() != null ? evento.getCategoria().getNombre() : null;

        EventoDTO eventoDTO = new EventoDTO(
            evento.getId(),
            evento.getNombre(),
            evento.getDescripcion(),
            evento.getDireccion(),
            evento.getFecha(),
            evento.getFechaCreacion(),
            tipoEventoNombre,
            subtipoEventoNombre,
            categoriaNombre
        );

        return ResponseEntity.ok(eventoDTO);
    }

    // Solo los administradores pueden crear un evento
    @PostMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<Evento> crear(@RequestBody Evento evento) {
        // Validar campos requeridos
        if (evento.getNombre() == null || evento.getNombre().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        if (evento.getFecha() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        if (evento.getTipoEvento() == null || evento.getTipoEvento().getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // Guardar el evento sin necesidad de latitud y longitud
        Evento nuevoEvento = eventoService.guardar(evento);
        return ResponseEntity.ok(nuevoEvento);
    }

    // Solo los administradores pueden actualizar un evento
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<Evento> actualizar(@PathVariable Long id, @RequestBody Evento evento) {
        // Verificar si el evento existe
        Evento eventoExistente = eventoService.obtenerPorId(id);
        if (eventoExistente == null) {
            return ResponseEntity.notFound().build();
        }

        // Actualizar solo los campos necesarios
        evento.setId(id);  // Mantener el mismo ID
        evento.setFechaCreacion(eventoExistente.getFechaCreacion());  // Mantener la fecha de creación original

        // Guardar y retornar el evento actualizado
        Evento eventoActualizado = eventoService.guardar(evento);
        return ResponseEntity.ok(eventoActualizado);
    }

    // Solo los administradores pueden eliminar un evento
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        // Verificar si el evento existe
        Evento evento = eventoService.obtenerPorId(id);
        if (evento == null) {
            return ResponseEntity.notFound().build();
        }

        // Eliminar el evento
        eventoService.eliminar(id);
        return ResponseEntity.noContent().build();  // Retornar respuesta vacía indicando éxito
    }
}
