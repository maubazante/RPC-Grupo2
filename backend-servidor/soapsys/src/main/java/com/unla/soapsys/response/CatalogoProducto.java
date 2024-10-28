package com.unla.soapsys.response;

public class CatalogoProducto {

	private Long id;

	private Catalogo catalogo;

	private Producto producto;

	public CatalogoProducto() {
		super();
	}

	public CatalogoProducto(Long id, Catalogo catalogo, Producto producto) {
		super();
		this.id = id;
		this.catalogo = catalogo;
		this.producto = producto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Catalogo getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(Catalogo catalogo) {
		this.catalogo = catalogo;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	@Override
	public String toString() {
		return "CatalogoProducto [id=" + id + ", catalogo=" + catalogo + ", producto=" + producto + "]";
	}

}
