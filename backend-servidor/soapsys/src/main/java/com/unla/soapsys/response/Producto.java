package com.unla.soapsys.response;

import java.util.List;

public class Producto {

	private Long id;

	private String nombre;

	private String codigo;

	private String talle;

	private String foto;

	private String color;

	private List<Stock> stock;

	private Integer cantidad;

	private boolean habilitado = true;

	public Producto(Long id, String nombre, String codigo, String foto, String color, String talle,
			boolean habilitado) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.codigo = codigo;
		this.talle = talle;
		this.foto = foto;
		this.color = color;
		this.habilitado = habilitado;
	}

	public Producto() {
		super();
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTalle() {
		return talle;
	}

	public void setTalle(String talle) {
		this.talle = talle;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

}
