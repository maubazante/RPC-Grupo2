package com.unla.stockearte.model;

import java.util.List;

import org.hibernate.mapping.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100)
	private String nombre;

	@Column(nullable = false, unique = true, length = 10)
	private String codigo;

	@Column(nullable = false, length = 5)
	private String talle;
	@Lob

	private byte[] foto; // La imagen se almacena como un blob

	@Column(nullable = false, length = 50)
	private String color;

	@OneToMany(mappedBy = "producto")
	private List<Stock> stock;

	@Column(name = "habilitado", nullable = false)
	private boolean habilitado = true;

	public Producto(Long id, String nombre, String codigo, byte[] foto, String color, String talle,
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

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
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

}