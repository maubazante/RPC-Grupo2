package com.unla.stockearte.dto;

public class OrdenCompraRequest {
	
	private Integer id; 
	private String codigoArticulo; 
	private String color; 
	private String talle;
	private Integer tienda;
	private Integer cantidadSolicitada;
	
	public OrdenCompraRequest(Integer id, String codigoArticulo, String color, String talle, Integer tienda,
			Integer cantidadSolicitada) {
		super();
		this.id = id;
		this.codigoArticulo = codigoArticulo;
		this.color = color;
		this.talle = talle;
		this.tienda = tienda;
		this.cantidadSolicitada = cantidadSolicitada;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigoArticulo() {
		return codigoArticulo;
	}

	public void setCodigoArticulo(String codigoArticulo) {
		this.codigoArticulo = codigoArticulo;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTalle() {
		return talle;
	}

	public void setTalle(String talle) {
		this.talle = talle;
	}

	public Integer getTienda() {
		return tienda;
	}

	public void setTienda(Integer tienda) {
		this.tienda = tienda;
	}

	public Integer getCantidadSolicitada() {
		return cantidadSolicitada;
	}

	public void setCantidadSolicitada(Integer cantidadSolicitada) {
		this.cantidadSolicitada = cantidadSolicitada;
	}
	
	

}
