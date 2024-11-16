package cl.eventos.deportivos.modelos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;

    private int edadMinima;
    private int edadMaxima;
   
 // Relación many-to-many con SubtipoEvento
    @ManyToMany
    @JoinTable(
        name = "categoria_subtipo_evento", // Nombre de la tabla intermedia
        joinColumns = @JoinColumn(name = "categoria_id"), // Columna para la categoría
        inverseJoinColumns = @JoinColumn(name = "subtipo_evento_id") // Columna para el subtipo de evento
    )
    @JsonIgnore  // Evitar recursión infinita
    private List<SubtipoEvento> subtiposEventos;// Un atributo que indica los subtipos de eventos asociados

    
    
	public Long getId() {
		return id;
	}

	

	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getEdadMinima() {
		return edadMinima;
	}
	public void setEdadMinima(int edadMinima) {
		this.edadMinima = edadMinima;
	}
	public int getEdadMaxima() {
		return edadMaxima;
	}
	public void setEdadMaxima(int edadMaxima) {
		this.edadMaxima = edadMaxima;
	}


	 public List<SubtipoEvento> getSubtiposEventos() {
	        return subtiposEventos;
	    }

	    public void setSubtiposEventos(List<SubtipoEvento> subtiposEventos) {
	        this.subtiposEventos = subtiposEventos;
	    }

	   
    
}

