package com.unla.stockearte.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombre", nullable = false, length = 100)
	private String nombre;

	@Column(name = "apellido", nullable = false, length = 100)
	private String apellido;

	@Column(name = "username", nullable = false, unique = true, length = 50)
	private String username;

	@Column(name = "password", nullable = false, length = 45)
	private String password;

	@Column(name = "habilitado", nullable = false)
	private Boolean habilitado = true;

	@Column(name = "rol", nullable = false)
	@Enumerated(EnumType.STRING)
	private Rol rol;

	@OneToOne
	@JoinColumn(name = "fk_tienda_id", referencedColumnName = "id")
	private Tienda tienda;

	public Usuario(Long id, String nombre, String apellido, String username, String password, Boolean habilitado,
			Rol rol, Tienda tienda) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.username = username;
		this.password = password;
		this.habilitado = habilitado;
		this.rol = rol;
		this.tienda = tienda;
	}

	public Usuario() {
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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Tienda getTienda() {
		return tienda;
	}

	public void setTienda(Tienda tienda) {
		this.tienda = tienda;
	}

}
