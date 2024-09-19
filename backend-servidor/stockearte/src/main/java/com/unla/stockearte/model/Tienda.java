package com.unla.stockearte.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tienda")
public class Tienda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "codigo", nullable = false, unique = true, length = 20)
	private String codigo;

	@Column(name = "direccion", nullable = false, length = 255)
	private String direccion;

	@Column(name = "ciudad", nullable = false, length = 100)
	private String ciudad;

	@Column(name = "provincia", nullable = false, length = 100)
	private String provincia;

	@Column(name = "habilitado", nullable = false)
	private Boolean habilitado = true;

	@OneToOne
	@JoinColumn(name = "fk_usuario_id", referencedColumnName = "id")
	private Usuario usuario;

	@OneToMany(mappedBy = "tienda", cascade = CascadeType.ALL)
	private List<Stock> productos;

	public Tienda(Long id, String codigo, String direccion, String ciudad, String provincia, Boolean habilitado,
			Usuario usuario, List<Stock> productos) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.direccion = direccion;
		this.ciudad = ciudad;
		this.provincia = provincia;
		this.habilitado = habilitado;
		this.usuario = usuario;
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

	public Boolean getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Stock> getProductos() {
		return productos;
	}

	public void setProductos(List<Stock> productos) {
		this.productos = productos;
	}

}