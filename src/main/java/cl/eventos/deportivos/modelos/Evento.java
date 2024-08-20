package cl.eventos.deportivos.modelos;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.JoinColumn;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	private String nombre;

	
	private LocalDate fecha;

	
	private String descripcion;

	
	private LocalDate fechaCreacion;
	
	@Column
	private String direccion;

	@Column
	private Double latitud;

	@Column
	private Double longitud;

	// Relaciones
	@OneToMany(mappedBy = "evento")
	private List<Inscripcion> inscripciones;

	@OneToMany(mappedBy = "evento")
	private List<Pago> pagos;

	@OneToMany(mappedBy = "evento")
	private List<Acreditacion> acreditaciones;

	@OneToMany(mappedBy = "evento")
	private List<Resultado> resultados;
	
	@ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

	@OneToMany(mappedBy = "evento")
	private List<Reporte> reportes;

	@OneToMany(mappedBy = "evento")
	private List<Estadistica> estadisticas;

	@ManyToMany
	@JoinTable(name = "evento_patrocinador", joinColumns = @JoinColumn(name = "evento_id"), inverseJoinColumns = @JoinColumn(name = "patrocinador_id"))
	private List<Patrocinador> patrocinadores;
	
	@ManyToOne
	@JoinColumn(name = "tipo_evento_id")
	private TipoEvento tipoEvento;

	@ManyToOne
	@JoinColumn(name = "subtipo_evento_id")
	private SubtipoEvento subtipoEvento;


	@PrePersist
	protected void onCreate() {
		this.fechaCreacion = LocalDate.now();
	}
	
	
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


	public LocalDate getFecha() {
		return fecha;
	}


	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}


	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}


	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	public List<Inscripcion> getInscripciones() {
		return inscripciones;
	}


	public void setInscripciones(List<Inscripcion> inscripciones) {
		this.inscripciones = inscripciones;
	}


	public List<Pago> getPagos() {
		return pagos;
	}


	public void setPagos(List<Pago> pagos) {
		this.pagos = pagos;
	}


	public List<Acreditacion> getAcreditaciones() {
		return acreditaciones;
	}


	public void setAcreditaciones(List<Acreditacion> acreditaciones) {
		this.acreditaciones = acreditaciones;
	}


	public List<Resultado> getResultados() {
		return resultados;
	}


	public void setResultados(List<Resultado> resultados) {
		this.resultados = resultados;
	}


	public Categoria getCategoria() {
		return categoria;
	}


	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}


	public List<Reporte> getReportes() {
		return reportes;
	}


	public void setReportes(List<Reporte> reportes) {
		this.reportes = reportes;
	}


	public List<Estadistica> getEstadisticas() {
		return estadisticas;
	}


	public void setEstadisticas(List<Estadistica> estadisticas) {
		this.estadisticas = estadisticas;
	}


	public List<Patrocinador> getPatrocinadores() {
		return patrocinadores;
	}


	public void setPatrocinadores(List<Patrocinador> patrocinadores) {
		this.patrocinadores = patrocinadores;
	}


	public Double getLatitud() {
		return latitud;
	}


	public Double getLongitud() {
		return longitud;
	}


	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}


	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}


	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}


	public SubtipoEvento getSubtipoEvento() {
		return subtipoEvento;
	}


	public void setSubtipoEvento(SubtipoEvento subtipoEvento) {
		this.subtipoEvento = subtipoEvento;
	}
	
}