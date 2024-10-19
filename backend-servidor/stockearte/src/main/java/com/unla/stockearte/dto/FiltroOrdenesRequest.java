package com.unla.stockearte.dto;

public class FiltroOrdenesRequest {

	private Long id;
	private String nombre;
	private Boolean filtroProducto;
	private Boolean filtroFecha;
	private Boolean filtroEstado;
	private Boolean filtroTienda;
	private Long userId;

	public FiltroOrdenesRequest() {
		super();
	}

	public FiltroOrdenesRequest(String nombre, Boolean filtroProducto, Boolean filtroFecha, Boolean filtroEstado,
			Boolean filtroTienda, Long userId) {
		super();
		this.nombre = nombre;
		this.filtroProducto = filtroProducto;
		this.filtroFecha = filtroFecha;
		this.filtroEstado = filtroEstado;
		this.filtroTienda = filtroTienda;
		this.userId = userId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean getFiltroProducto() {
		return filtroProducto;
	}

	public void setFiltroProducto(Boolean filtroProducto) {
		this.filtroProducto = filtroProducto;
	}

	public Boolean getFiltroFecha() {
		return filtroFecha;
	}

	public void setFiltroFecha(Boolean filtroFecha) {
		this.filtroFecha = filtroFecha;
	}

	public Boolean getFiltroEstado() {
		return filtroEstado;
	}

	public void setFiltroEstado(Boolean filtroEstado) {
		this.filtroEstado = filtroEstado;
	}

	public Boolean getFiltroTienda() {
		return filtroTienda;
	}

	public void setFiltroTienda(Boolean filtroTienda) {
		this.filtroTienda = filtroTienda;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
