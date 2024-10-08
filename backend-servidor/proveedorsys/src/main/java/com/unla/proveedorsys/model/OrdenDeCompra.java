package com.unla.proveedorsys.model;

import java.time.LocalDateTime;

import com.unla.proveedorsys.enums.EstadoOrden;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	@Column(name = "codigo_articulo", length = 50)
	private String codigoArticulo;
	
	@Column(name = "color", length = 50)
	private String color;
	
	@Column(name = "talle", length = 50)
	private String talle;
	
	@Column(name = "cantidad_solicitada", length = 500)
	private Integer cantidadSolicitada;

	@ManyToOne
	@JoinColumn(name = "tienda_id", nullable = false)
	private Tienda tienda;

	// Constructores, getters y setters
	public OrdenDeCompra() {
	}

	public OrdenDeCompra(Long id, EstadoOrden estado, String observaciones, Long id_orden_despacho,
			LocalDateTime fechaSolicitud, LocalDateTime fechaRecepcion, String codigoArticulo, String color,
			String talle, Integer cantidadSolicitada, Tienda tienda) {
		super();
		this.id = id;
		this.estado = estado;
		this.observaciones = observaciones;
		this.id_orden_despacho = id_orden_despacho;
		this.fechaSolicitud = fechaSolicitud;
		this.fechaRecepcion = fechaRecepcion;
		this.codigoArticulo = codigoArticulo;
		this.color = color;
		this.talle = talle;
		this.cantidadSolicitada = cantidadSolicitada;
		this.tienda = tienda;
	}

	public OrdenDeCompra(String codigoArticulo, String color, String talle, Integer cantidadSolicitada, Tienda tienda) {
		super();
		this.codigoArticulo = codigoArticulo;
		this.color = color;
		this.talle = talle;
		this.cantidadSolicitada = cantidadSolicitada;
		this.tienda = tienda;
	}

	public OrdenDeCompra(Long id, EstadoOrden estado, String codigoArticulo, String color, String talle,
			Integer cantidadSolicitada, Tienda tienda) {
		super();
		this.id = id;
		this.estado = estado;
		this.codigoArticulo = codigoArticulo;
		this.color = color;
		this.talle = talle;
		this.cantidadSolicitada = cantidadSolicitada;
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

	public Long getId_orden_despacho() {
		return id_orden_despacho;
	}

	public void setId_orden_despacho(Long id_orden_despacho) {
		this.id_orden_despacho = id_orden_despacho;
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

	public String getCodigoArticulo() {
		return codigoArticulo;
	}

	public void setCodigoArticulo(String codigoArticulo) {
		this.codigoArticulo = codigoArticulo;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTalle() {
		return talle;
	}

	public void setTalle(String talle) {
		this.talle = talle;
	}

	public Integer getCantidadSolicitada() {
		return cantidadSolicitada;
	}

	public void setCantidadSolicitada(Integer cantidadSolicitada) {
		this.cantidadSolicitada = cantidadSolicitada;
	}

	public Tienda getTienda() {
		return tienda;
	}

	public void setTienda(Tienda tienda) {
		this.tienda = tienda;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
