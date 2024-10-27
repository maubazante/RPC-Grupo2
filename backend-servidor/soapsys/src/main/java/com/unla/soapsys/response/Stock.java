package com.unla.soapsys.response;

public class Stock {

	private StockId id;

	private Producto producto;
	
	private Tienda tienda;

	private Integer stock = 0;

	public Stock(StockId id, Tienda tienda, Producto producto, Integer stock) {
		super();
		this.id = id;
		this.tienda = tienda;
		this.producto = producto;
		this.stock = stock;
	}

	public Stock() {
		super();
	}

	public StockId getId() {
		return id;
	}

	public void setId(StockId id) {
		this.id = id;
	}

	public Tienda getTienda() {
		return tienda;
	}

	public void setTienda(Tienda tienda) {
		this.tienda = tienda;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

}