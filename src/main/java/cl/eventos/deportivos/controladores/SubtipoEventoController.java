package cl.eventos.deportivos.controladores;

import cl.eventos.deportivos.modelos.SubtipoEvento;
import cl.eventos.deportivos.servicios.SubtipoEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subtipos-evento")
public class SubtipoEventoController {

    @Autowired
    private SubtipoEventoService subtipoEventoService;

    @GetMapping
    public ResponseEntity<List<SubtipoEvento>> obtenerTodosLosSubtiposDeEvento() {
        List<SubtipoEvento> subtiposEvento = subtipoEventoService.obtenerTodosLosSubtiposDeEvento();
        return ResponseEntity.ok(subtiposEvento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubtipoEvento> obtenerSubtipoEventoPorId(@PathVariable Long id) {
        SubtipoEvento subtipoEvento = subtipoEventoService.obtenerSubtipoEventoPorId(id);
        return ResponseEntity.ok(subtipoEvento);
    }

    @PostMapping
    public ResponseEntity<SubtipoEvento> crearSubtipoEvento(@RequestBody SubtipoEvento subtipoEvento) {
        SubtipoEvento nuevoSubtipoEvento = subtipoEventoService.crearSubtipoEvento(subtipoEvento);
        return ResponseEntity.ok(nuevoSubtipoEvento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubtipoEvento> actualizarSubtipoEvento(@PathVariable Long id, @RequestBody SubtipoEvento subtipoEvento) {
        SubtipoEvento subtipoEventoActualizado = subtipoEventoService.actualizarSubtipoEvento(id, subtipoEvento);
        return ResponseEntity.ok(subtipoEventoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSubtipoEvento(@PathVariable Long id) {
        subtipoEventoService.eliminarSubtipoEvento(id);
        return ResponseEntity.noContent().build();
    }
}

