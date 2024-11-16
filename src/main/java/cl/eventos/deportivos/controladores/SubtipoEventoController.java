package cl.eventos.deportivos.controladores;

import cl.eventos.deportivos.modelos.SubtipoEvento;
import cl.eventos.deportivos.servicios.SubtipoEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subtipos-evento")
public class SubtipoEventoController {

	@Autowired
	private SubtipoEventoService subtipoEventoService;

	// Obtener todos los subtipos de evento
	@GetMapping
	@PreAuthorize("hasAuthority('Administrador')")
	public ResponseEntity<List<SubtipoEvento>> obtenerTodosLosSubtiposDeEvento() {
		List<SubtipoEvento> subtiposEvento = subtipoEventoService.obtenerTodosLosSubtiposDeEvento();
		return ResponseEntity.ok(subtiposEvento);
	}

	// Obtener subtipo de evento por ID
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('Administrador')")
	public ResponseEntity<SubtipoEvento> obtenerSubtipoEventoPorId(@PathVariable Long id) {
		SubtipoEvento subtipoEvento = subtipoEventoService.obtenerSubtipoEventoPorId(id);
		return ResponseEntity.ok(subtipoEvento);
	}

	// Crear un nuevo subtipo de evento y asignar categorías
	@PostMapping
	@PreAuthorize("hasAuthority('Administrador')")
	public ResponseEntity<SubtipoEvento> crearSubtipoEvento(@RequestBody Map<String, Object> payload) {
	    SubtipoEvento subtipoEvento = new SubtipoEvento();
	    
	    // Extraer los campos necesarios desde el payload
	    subtipoEvento.setNombre((String) payload.get("nombre"));
	    subtipoEvento.setDescripcion((String) payload.get("descripcion"));
	    
	 // Extraer el ID del tipo de evento
	    Map<String, Object> tipoEventoMap = (Map<String, Object>) payload.get("tipoEvento");
	    Long tipoEventoId = ((Number) tipoEventoMap.get("id")).longValue();

	    
	    // Extraer los IDs de las categorías, si existen y si no están vacías
	    List<Long> categoriaIds = null;
	    if (payload.get("categorias") != null && !((List<?>) payload.get("categorias")).isEmpty()) {
	        categoriaIds = ((List<?>) payload.get("categorias")).stream()
	            .filter(id -> id instanceof Number)
	            .map(id -> ((Number) id).longValue())
	            .collect(Collectors.toList());
	    }

	    // Llamar al servicio para crear el SubtipoEvento
	    SubtipoEvento nuevoSubtipoEvento = subtipoEventoService.crearSubtipoEvento(subtipoEvento, tipoEventoId, categoriaIds);
	    
	    return ResponseEntity.ok(nuevoSubtipoEvento);
	}





	// Actualizar un subtipo de evento por ID y reasignar categorías
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('Administrador')")
	public ResponseEntity<SubtipoEvento> actualizarSubtipoEvento(@PathVariable Long id,
			@RequestBody Map<String, Object> payload) {
		SubtipoEvento subtipoEvento = new SubtipoEvento();

		// Extraer los campos necesarios desde el payload
		subtipoEvento.setNombre((String) payload.get("nombre"));
		subtipoEvento.setDescripcion((String) payload.get("descripcion"));

		// Extraer los IDs de las categorías (si los hay) desde el payload
		List<Long> categoriaIds = (List<Long>) payload.get("categoriaIds");

		// Actualizar el subtipo de evento con las nuevas categorías
		SubtipoEvento subtipoEventoActualizado = subtipoEventoService.actualizarSubtipoEvento(id, subtipoEvento,
				categoriaIds);
		return ResponseEntity.ok(subtipoEventoActualizado);
	}

	// Eliminar un subtipo de evento por ID
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('Administrador')")
	public ResponseEntity<Void> eliminarSubtipoEvento(@PathVariable Long id) {
		subtipoEventoService.eliminarSubtipoEvento(id);
		return ResponseEntity.noContent().build();
	}
}
