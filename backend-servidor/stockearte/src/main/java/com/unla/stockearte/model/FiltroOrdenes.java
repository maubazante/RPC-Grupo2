package com.unla.stockearte.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "filtro_de_ordenes")
public class FiltroOrdenes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombre", nullable = true, length = 100)
	private String nombre;

	@Column(name = "filtro_codigo_producto", nullable = true)
	private Boolean filtroProducto;

	@Column(name = "filtro_rango_de_fechas", nullable = true)
	private Boolean filtroFecha;

	@Column(name = "filtro_estado", nullable = true)
	private Boolean filtroEstado;

	@Column(name = "filtro_tienda", nullable = true)
	private Boolean filtroTienda;

	@Column(name = "fk_usuarios_id")
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
