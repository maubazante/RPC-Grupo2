package com.unla.stockearte.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "ordenes_compra")
public class OrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tienda_id", nullable = false)
    private Tienda tienda;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "orden_id")
    private List<ItemOrden> items;

    @Column(nullable = false)
    private LocalDate fechaSolicitud;

    @Column(nullable = false)
    private String estado;

    @Column
    private String observaciones;

    @Column
    private String ordenDespacho;

    @Column
    private Date fechaRecepcion;

	public OrdenCompra(Long id, Tienda tienda, List<ItemOrden> items, LocalDate fechaSolicitud, String estado,
			String observaciones, String ordenDespacho, Date fechaRecepcion) {
		super();
		this.id = id;
		this.tienda = tienda;
		this.items = items;
		this.fechaSolicitud = fechaSolicitud;
		this.estado = estado;
		this.observaciones = observaciones;
		this.ordenDespacho = ordenDespacho;
		this.fechaRecepcion = fechaRecepcion;
	}

	public OrdenCompra() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Tienda getTienda() {
		return tienda;
	}

	public void setTienda(Tienda tienda) {
		this.tienda = tienda;
	}

	public List<ItemOrden> getItems() {
		return items;
	}

	public void setItems(List<ItemOrden> items) {
		this.items = items;
	}

	public LocalDate getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(LocalDate localDate) {
		this.fechaSolicitud = localDate;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getOrdenDespacho() {
		return ordenDespacho;
	}

	public void setOrdenDespacho(String ordenDespacho) {
		this.ordenDespacho = ordenDespacho;
	}

	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}

	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}
	
	

}
