package cl.eventos.deportivos.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.eventos.deportivos.modelos.Inscripcion;
import cl.eventos.deportivos.servicios.InscripcionService;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    @Autowired
    private InscripcionService inscripcionService;

    @GetMapping
    public List<Inscripcion> listarTodos() {
        return inscripcionService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inscripcion> obtenerPorId(@PathVariable Long id) {
        Inscripcion inscripcion = inscripcionService.obtenerPorId(id);
        return inscripcion != null ? ResponseEntity.ok(inscripcion) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Inscripcion crear(@RequestBody Inscripcion inscripcion) {
        return inscripcionService.guardar(inscripcion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inscripcion> actualizar(@PathVariable Long id, @RequestBody Inscripcion inscripcion) {
        if (inscripcionService.obtenerPorId(id) == null) {
            return ResponseEntity.notFound().build();
        }
        inscripcion.setId(id);
        return ResponseEntity.ok(inscripcionService.guardar(inscripcion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (inscripcionService.obtenerPorId(id) == null) {
            return ResponseEntity.notFound().build();
        }
        inscripcionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}