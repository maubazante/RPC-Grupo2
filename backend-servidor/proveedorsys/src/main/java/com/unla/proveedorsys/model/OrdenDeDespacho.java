package com.unla.proveedorsys.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orden_de_despacho")
public class OrdenDeDespacho {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "id_orden_compra", referencedColumnName = "id", nullable = false)
	private OrdenDeCompra ordenDeCompra;

	@Column(name = "fecha_de_envio")
	private LocalDateTime fechaDeEnvio;

	public OrdenDeDespacho() {
		super();
	}

	public OrdenDeDespacho(Long id, OrdenDeCompra ordenDeCompra, LocalDateTime fechaDeEnvio) {
		super();
		this.id = id;
		this.ordenDeCompra = ordenDeCompra;
		this.fechaDeEnvio = fechaDeEnvio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrdenDeCompra getOrdenDeCompra() {
		return ordenDeCompra;
	}

	public void setOrdenDeCompra(OrdenDeCompra ordenDeCompra) {
		this.ordenDeCompra = ordenDeCompra;
	}

	public LocalDateTime getFechaDeEnvio() {
		return fechaDeEnvio;
	}

	public void setFechaDeEnvio(LocalDateTime fechaDeEnvio) {
		this.fechaDeEnvio = fechaDeEnvio;
	}

}
