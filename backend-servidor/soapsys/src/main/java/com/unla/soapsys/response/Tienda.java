package com.unla.soapsys.response;

import java.util.List;


public class Tienda {

	private Long id;

	private String codigo;

	private String direccion;

	private String ciudad;

	private String provincia;

	private Boolean habilitada = true;

	private Boolean esCasaCentral = false;

	private List<Stock> productos;

	public Tienda(Long id, String codigo, String direccion, String ciudad, String provincia, Boolean habilitada,
			Boolean esCasaCentral, List<Stock> productos) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.direccion = direccion;
		this.ciudad = ciudad;
		this.provincia = provincia;
		this.habilitada = habilitada;
		this.esCasaCentral = esCasaCentral;
		this.productos = productos;
	}

	public Tienda() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public Boolean isHabilitada() {
		return habilitada;
	}

	public void setHabilitada(Boolean habilitada) {
		this.habilitada = habilitada;
	}

	public Boolean getEsCasaCentral() {
		return esCasaCentral;
	}

	public void setEsCasaCentral(Boolean esCasaCentral) {
		this.esCasaCentral = esCasaCentral;
	}

	public List<Stock> getProductos() {
		return productos;
	}

	public void setProductos(List<Stock> productos) {
		this.productos = productos;
	}

}
