package cl.eventos.deportivos.dto;

import java.util.List;

public class CategoriaDTO {
	
	private String nombre;
    private int edadMinima;
    private int edadMaxima;
    private List<Long> subtipoEventoIds; // Lista de IDs de subtipos de eventos
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
	public List<Long> getSubtipoEventoIds() {
		return subtipoEventoIds;
	}
	public void setSubtipoEventoIds(List<Long> subtipoEventoIds) {
		this.subtipoEventoIds = subtipoEventoIds;
	}
    
    

}
