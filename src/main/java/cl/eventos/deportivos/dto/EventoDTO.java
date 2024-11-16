package cl.eventos.deportivos.dto;

import java.time.LocalDate;

public class EventoDTO {

	 private Long id;
	    private String nombre;
	    private String descripcion;
	    private String direccion;
	    private LocalDate fecha;
	    private LocalDate fechaCreacion;
	    private String tipoEventoNombre;
	    private String subtipoEventoNombre;
	    private String categoriaNombre;

	    // Constructor
	    public EventoDTO(Long id, String nombre, String descripcion, String direccion, LocalDate fecha, LocalDate fechaCreacion, String tipoEventoNombre, String subtipoEventoNombre, String categoriaNombre) {
	        this.id = id;
	        this.nombre = nombre;
	        this.descripcion = descripcion;
	        this.direccion = direccion;
	        this.fecha = fecha;
	        this.fechaCreacion = fechaCreacion;
	        this.tipoEventoNombre = tipoEventoNombre;
	        this.subtipoEventoNombre = subtipoEventoNombre;
	        this.categoriaNombre = categoriaNombre;
	    }

	    // Getters y Setters
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

	    public String getDescripcion() {
	        return descripcion;
	    }

	    public void setDescripcion(String descripcion) {
	        this.descripcion = descripcion;
	    }

	    public String getDireccion() {
	        return direccion;
	    }

	    public void setDireccion(String direccion) {
	        this.direccion = direccion;
	    }

	    public LocalDate getFecha() {
	        return fecha;
	    }

	    public void setFecha(LocalDate fecha) {
	        this.fecha = fecha;
	    }

	    public LocalDate getFechaCreacion() {
	        return fechaCreacion;
	    }

	    public void setFechaCreacion(LocalDate fechaCreacion) {
	        this.fechaCreacion = fechaCreacion;
	    }

	    public String getTipoEventoNombre() {
	        return tipoEventoNombre;
	    }

	    public void setTipoEventoNombre(String tipoEventoNombre) {
	        this.tipoEventoNombre = tipoEventoNombre;
	    }

	    public String getSubtipoEventoNombre() {
	        return subtipoEventoNombre;
	    }

	    public void setSubtipoEventoNombre(String subtipoEventoNombre) {
	        this.subtipoEventoNombre = subtipoEventoNombre;
	    }

	    public String getCategoriaNombre() {
	        return categoriaNombre;
	    }

	    public void setCategoriaNombre(String categoriaNombre) {
	        this.categoriaNombre = categoriaNombre;
	    }
	}
