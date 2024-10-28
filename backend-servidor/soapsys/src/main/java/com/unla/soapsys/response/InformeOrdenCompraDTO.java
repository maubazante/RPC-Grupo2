package com.unla.soapsys.response;

import java.util.ArrayList;
import java.util.List;

public class InformeOrdenCompraDTO {

	private Long id;
	private String fechaSolicitud;
	private EstadoOrden estado;
	private List<Producto> producto = new ArrayList<>();
	private Tienda tienda;

	public InformeOrdenCompraDTO() {
		super();
	}

	public InformeOrdenCompraDTO(Long id, String fechaSolicitud, EstadoOrden estado, List<Producto> producto,
			Tienda tienda) {
		super();
		this.id = id;
		this.fechaSolicitud = fechaSolicitud;
		this.estado = estado;
		this.producto = producto;
		this.tienda = tienda;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public EstadoOrden getEstado() {
		return estado;
	}

	public void setEstado(EstadoOrden estado) {
		this.estado = estado;
	}

	public List<Producto> getProducto() {
		return producto;
	}

	public void setProducto(List<Producto> producto) {
		this.producto = producto;
	}

	public Tienda getTienda() {
		return tienda;
	}

	public void setTienda(Tienda tienda) {
		this.tienda = tienda;
	}

}
