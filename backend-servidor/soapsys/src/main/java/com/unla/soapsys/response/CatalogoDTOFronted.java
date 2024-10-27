package com.unla.soapsys.response;

import java.util.List;

public class CatalogoDTOFronted {

	private Long idTienda;
	private Long id;
	private String nombre;
	private List<Long> productosIds;

	public CatalogoDTOFronted() {
		super();
	}

	public CatalogoDTOFronted(Long idTienda, Long id, String nombre, List<Long> productosIds) {
		super();
		this.idTienda = idTienda;
		this.id = id;
		this.nombre = nombre;
		this.productosIds = productosIds;
	}

	public Long getIdTienda() {
		return idTienda;
	}

	public void setIdTienda(Long idTienda) {
		this.idTienda = idTienda;
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

	public List<Long> getProductosIds() {
		return productosIds;
	}

	public void setProductosIds(List<Long> productosIds) {
		this.productosIds = productosIds;
	}

}
