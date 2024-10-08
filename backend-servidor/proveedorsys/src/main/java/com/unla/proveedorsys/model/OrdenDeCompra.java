package com.unla.proveedorsys.model;

import java.time.LocalDateTime;
import java.util.List;

import com.unla.proveedorsys.enums.EstadoOrden;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orden_de_compra")
public class OrdenDeCompra {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "estado", nullable = false)
	@Enumerated(EnumType.STRING)
	private EstadoOrden estado;

	@Column(name = "id_orden_despacho")
	private Long id_orden_despacho;

	@Column(name = "observaciones", length = 500)
	private String observaciones;

	@Column(name = "fecha_solicitud", nullable = false)
	private LocalDateTime fechaSolicitud;

	@Column(name = "fecha_recepcion")
	private LocalDateTime fechaRecepcion;

	@OneToMany(mappedBy = "ordenDeCompra", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ItemOrden> items;

	@ManyToOne
	@JoinColumn(name = "tienda_id", nullable = false)
	private Tienda tienda;

	// Constructores, getters y setters
	public OrdenDeCompra() {
	}

	public OrdenDeCompra(Long id, EstadoOrden estado, Long id_orden_despacho, String observaciones,
			LocalDateTime fechaSolicitud, LocalDateTime fechaRecepcion, List<ItemOrden> items, Tienda tienda) {
		super();
		this.id = id;
		this.estado = estado;
		this.id_orden_despacho = id_orden_despacho;
		this.observaciones = observaciones;
		this.fechaSolicitud = fechaSolicitud;
		this.fechaRecepcion = fechaRecepcion;
		this.items = items;
		this.tienda = tienda;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EstadoOrden getEstado() {
		return estado;
	}

	public void setEstado(EstadoOrden estado) {
		this.estado = estado;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public LocalDateTime getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public LocalDateTime getFechaRecepcion() {
		return fechaRecepcion;
	}

	public void setFechaRecepcion(LocalDateTime fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	public List<ItemOrden> getItems() {
		return items;
	}

	public void setItems(List<ItemOrden> items) {
		this.items = items;
	}

	public Tienda getTienda() {
		return tienda;
	}

	public void setTienda(Tienda tienda) {
		this.tienda = tienda;
	}

	public Long getId_orden_despacho() {
		return id_orden_despacho;
	}

	public void setId_orden_despacho(Long id_orden_despacho) {
		this.id_orden_despacho = id_orden_despacho;
	}

}
