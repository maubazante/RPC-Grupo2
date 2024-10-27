package com.unla.soapsys.response;

import java.util.List;

public class CatalogoDTO {
	private String nombre;
	private Long tiendaId;
	private List<Long> productoIds;

	public CatalogoDTO() {
		super();
	}

	public CatalogoDTO(String nombre, Long tiendaId, List<Long> productoIds) {
		super();
		this.nombre = nombre;
		this.tiendaId = tiendaId;
		this.productoIds = productoIds;
	}

	// Getters y Setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getTiendaId() {
		return tiendaId;
	}

	public void setTiendaId(Long tiendaId) {
		this.tiendaId = tiendaId;
	}

	public List<Long> getProductoIds() {
		return productoIds;
	}

	public void setProductoIds(List<Long> productoIds) {
		this.productoIds = productoIds;
	}
}
