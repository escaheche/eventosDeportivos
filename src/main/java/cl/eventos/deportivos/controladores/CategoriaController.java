package cl.eventos.deportivos.controladores;

import cl.eventos.deportivos.modelos.Categoria;
import cl.eventos.deportivos.modelos.Usuario;
import cl.eventos.deportivos.servicios.CategoriaService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<List<Categoria>> obtenerTodasLasCategorias() {
        List<Categoria> categorias = categoriaService.obtenerTodasLasCategorias();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<Categoria> obtenerCategoriaPorId(@PathVariable Long id) {
        Categoria categoria = categoriaService.obtenerCategoriaPorId(id);
        return ResponseEntity.ok(categoria);
    }

    // Crear una nueva categoría
    @PostMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<Categoria> crearCategoria(@Valid @RequestBody Categoria categoria) {
        Categoria nuevaCategoria = categoriaService.crearCategoria(categoria);
        return ResponseEntity.ok(nuevaCategoria);
    }

    // Actualizar una categoría existente
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        Categoria categoriaActualizada = categoriaService.actualizarCategoria(id, categoria);
        return ResponseEntity.ok(categoriaActualizada);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }

    // Asignar una categoría según la fecha de nacimiento y la fecha de competencia
    @PostMapping("/asignar")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<Categoria> asignarCategoria(@RequestBody Usuario usuario, 
                                                      @RequestParam("fechaCompetencia") String fechaCompetenciaStr) {
        LocalDate fechaCompetencia = LocalDate.parse(fechaCompetenciaStr);
        Categoria categoria = categoriaService.asignarCategoriaPorFechaNacimientoYDistancia(usuario, fechaCompetencia);
        return ResponseEntity.ok(categoria);
    }
}
