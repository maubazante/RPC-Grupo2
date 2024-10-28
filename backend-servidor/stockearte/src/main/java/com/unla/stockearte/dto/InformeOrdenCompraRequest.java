package com.unla.stockearte.dto;

import java.time.LocalDate;

import com.unla.stockearte.enums.EstadoOrden;
import com.unla.stockearte.model.Tienda;

public class InformeOrdenCompraRequest {

	private String codigoArticulo;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private EstadoOrden estado;
	private Long tiendaId;

	public InformeOrdenCompraRequest() {
		super();
	}

	public InformeOrdenCompraRequest(String codigoArticulo, LocalDate fechaInicio, LocalDate fechaFin,
			EstadoOrden estado, Long tiendaId) {
		super();
		this.codigoArticulo = codigoArticulo;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.estado = estado;
		this.tiendaId = tiendaId;
	}

	public String getCodigoArticulo() {
		return codigoArticulo;
	}

	public void setCodigoArticulo(String codigoArticulo) {
		this.codigoArticulo = codigoArticulo;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public EstadoOrden getEstado() {
		return estado;
	}

	public void setEstado(EstadoOrden estado) {
		this.estado = estado;
	}

	public Long getTiendaId() {
		return tiendaId;
	}

	public void setTiendaId(Long tiendaId) {
		this.tiendaId = tiendaId;
	}

}
