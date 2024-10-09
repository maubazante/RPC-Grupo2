package com.unla.stockearte.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "stock")
public class Stock {

	@EmbeddedId
	private StockId id;

	@ManyToOne
	@MapsId("productoId")
	@JoinColumn(name = "fk_producto_id", nullable = false)
	@JsonBackReference
	private Producto producto;

	@ManyToOne
	@MapsId("tiendaId")
	@JoinColumn(name = "fk_tienda_id", nullable = false)
	private Tienda tienda;

	@Column(nullable = false)
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
