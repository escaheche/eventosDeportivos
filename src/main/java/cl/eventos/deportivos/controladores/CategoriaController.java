package cl.eventos.deportivos.controladores;

import cl.eventos.deportivos.modelos.Categoria;
import cl.eventos.deportivos.modelos.Usuario;
import cl.eventos.deportivos.servicios.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@PreAuthorize("hasAuthority('Administrador')")
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<Categoria>> obtenerTodasLasCategorias() {
        List<Categoria> categorias = categoriaService.obtenerTodasLasCategorias();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerCategoriaPorId(@PathVariable Long id) {
        Categoria categoria = categoriaService.obtenerCategoriaPorId(id);
        return ResponseEntity.ok(categoria);
    }

    @PostMapping
    public ResponseEntity<Categoria> crearCategoria(@RequestBody Categoria categoria) {
        Categoria nuevaCategoria = categoriaService.crearCategoria(categoria);
        return ResponseEntity.ok(nuevaCategoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        Categoria categoriaActualizada = categoriaService.actualizarCategoria(id, categoria);
        return ResponseEntity.ok(categoriaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/asignar")
    public ResponseEntity<Categoria> asignarCategoria(@RequestBody Usuario usuario, 
                                                      @RequestParam("distancia") double distancia,
                                                      @RequestParam("fechaCompetencia") String fechaCompetenciaStr) {
        LocalDate fechaCompetencia = LocalDate.parse(fechaCompetenciaStr);
        Categoria categoria = categoriaService.asignarCategoriaPorFechaNacimientoYDistancia(usuario, distancia, fechaCompetencia);
        return ResponseEntity.ok(categoria);
    }
}
