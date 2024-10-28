package com.unla.stockearte.model;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "catalogos")
public class Catalogo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nombre;

	@ManyToOne
	@JoinColumn(name = "fk_tienda_id", nullable = false)
	private Tienda tienda;

	@OneToMany(mappedBy = "catalogo", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<CatalogoProducto> catalogoProductos;

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

	public Tienda getTienda() {
		return tienda;
	}

	public void setTienda(Tienda tienda) {
		this.tienda = tienda;
	}

	public List<Producto> getProductos() {
		return catalogoProductos.stream().map(CatalogoProducto::getProducto).collect(Collectors.toList());
	}

	public void setCatalogoProductos(List<CatalogoProducto> catalogoProductos) {
		this.catalogoProductos = catalogoProductos;
	}

	public List<CatalogoProducto> getCatalogoProductos() {
		return catalogoProductos;
	}

	@Override
	public String toString() {
		return "Catalogo [id=" + id + ", nombre=" + nombre + ", tienda=" + tienda + ", catalogoProductos="
				+ catalogoProductos + "]";
	}

}
