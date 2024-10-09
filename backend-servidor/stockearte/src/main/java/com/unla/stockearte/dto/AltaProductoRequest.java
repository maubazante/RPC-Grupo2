package com.unla.stockearte.dto;

import java.util.ArrayList;

public class AltaProductoRequest {
	public int id;
	public String codigo;
	public String nombre;
	public String talle;
	public String color;
	public String foto;
	public int cantidad;
	public ArrayList<Integer> tiendaIds;
	public boolean habilitado;
	public int idUserAdmin;
	
	public AltaProductoRequest(int id, String codigo, String nombre, String talle, String color, String foto,
			int cantidad, ArrayList<Integer> tiendaIds, boolean habilitado, int idUserAdmin) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nombre = nombre;
		this.talle = talle;
		this.color = color;
		this.foto = foto;
		this.cantidad = cantidad;
		this.tiendaIds = tiendaIds;
		this.habilitado = habilitado;
		this.idUserAdmin = idUserAdmin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTalle() {
		return talle;
	}

	public void setTalle(String talle) {
		this.talle = talle;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public ArrayList<Integer> getTiendaIds() {
		return tiendaIds;
	}

	public void setTiendaIds(ArrayList<Integer> tiendaIds) {
		this.tiendaIds = tiendaIds;
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	public int getIdUserAdmin() {
		return idUserAdmin;
	}

	public void setIdUserAdmin(int idUserAdmin) {
		this.idUserAdmin = idUserAdmin;
	}
	
	
}
