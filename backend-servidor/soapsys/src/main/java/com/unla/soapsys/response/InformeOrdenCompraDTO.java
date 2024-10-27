package com.unla.soapsys.response;

public class InformeOrdenCompraDTO {

	private String codigoArticulo;
	private EstadoOrden estado;
	private Tienda tienda;
	private Long totalCantidad;

	public InformeOrdenCompraDTO() {
		super();
	}

	public InformeOrdenCompraDTO(String codigoArticulo, EstadoOrden estado, Tienda tienda, Long totalCantidad) {
		super();
		this.codigoArticulo = codigoArticulo;
		this.estado = estado;
		this.tienda = tienda;
		this.totalCantidad = totalCantidad;
	}

	public String getCodigoArticulo() {
		return codigoArticulo;
	}

	public void setCodigoArticulo(String codigoArticulo) {
		this.codigoArticulo = codigoArticulo;
	}

	public EstadoOrden getEstado() {
		return estado;
	}

	public void setEstado(EstadoOrden estado) {
		this.estado = estado;
	}

	public Tienda getTienda() {
		return tienda;
	}

	public void setTienda(Tienda tienda) {
		this.tienda = tienda;
	}

	public Long getTotalCantidad() {
		return totalCantidad;
	}

	public void setTotalCantidad(Long totalCantidad) {
		this.totalCantidad = totalCantidad;
	}

}
