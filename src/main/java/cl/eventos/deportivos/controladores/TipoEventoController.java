package cl.eventos.deportivos.controladores;

import cl.eventos.deportivos.modelos.TipoEvento;
import cl.eventos.deportivos.servicios.TipoEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-evento")
public class TipoEventoController {

    @Autowired
    private TipoEventoService tipoEventoService;

    @GetMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<List<TipoEvento>> obtenerTodosLosTiposDeEvento() {
        List<TipoEvento> tiposEvento = tipoEventoService.obtenerTodosLosTiposDeEvento();
        return ResponseEntity.ok(tiposEvento);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<TipoEvento> obtenerTipoEventoPorId(@PathVariable Long id) {
        TipoEvento tipoEvento = tipoEventoService.obtenerTipoEventoPorId(id);
        return ResponseEntity.ok(tipoEvento);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<TipoEvento> crearTipoEvento(@RequestBody TipoEvento tipoEvento) {
        TipoEvento nuevoTipoEvento = tipoEventoService.crearTipoEvento(tipoEvento);
        return ResponseEntity.ok(nuevoTipoEvento);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<TipoEvento> actualizarTipoEvento(@PathVariable Long id, @RequestBody TipoEvento tipoEvento) {
        TipoEvento tipoEventoActualizado = tipoEventoService.actualizarTipoEvento(id, tipoEvento);
        return ResponseEntity.ok(tipoEventoActualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<Void> eliminarTipoEvento(@PathVariable Long id) {
        tipoEventoService.eliminarTipoEvento(id);
        return ResponseEntity.noContent().build();
    }
}

