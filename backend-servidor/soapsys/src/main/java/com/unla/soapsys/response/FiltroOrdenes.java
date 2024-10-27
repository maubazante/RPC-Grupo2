package com.unla.soapsys.response;

public class FiltroOrdenes {

	private Long id;

	private String nombre;

	private Boolean filtroProducto;

	private Boolean filtroFecha;

	private Boolean filtroEstado;

	private Boolean filtroTienda;

	private Long fkUsuariosId;

	public FiltroOrdenes() {
		super();
	}

	public FiltroOrdenes(Long id, String nombre, Boolean filtroProducto, Boolean filtroFecha, Boolean filtroEstado,
			Boolean filtroTienda) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.filtroProducto = filtroProducto;
		this.filtroFecha = filtroFecha;
		this.filtroEstado = filtroEstado;
		this.filtroTienda = filtroTienda;
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

	public Long getFkUsuariosId() {
		return fkUsuariosId;
	}

	public void setFkUsuariosId(Long fkUsuariosId) {
		this.fkUsuariosId = fkUsuariosId;
	}

}