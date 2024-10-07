package com.unla.proveedorsys.model;

import jakarta.persistence.*;
import java.io.Serializable;

@SuppressWarnings("serial")
@Entity
@Table(name = "item_orden")
public class ItemOrden implements Serializable {
	
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_articulo", nullable = false, length = 50)
    private String codigoArticulo;

    @Column(name = "color", nullable = false, length = 30)
    private String color;

    @Column(name = "talle", nullable = false, length = 10)
    private String talle;

    @Column(name = "cantidad_solicitada", nullable = false)
    private Integer cantidadSolicitada;

    @ManyToOne
    @JoinColumn(name = "orden_id", nullable = false)
    private OrdenDeCompra ordenDeCompra;

    // Constructores, getters y setters

    public ItemOrden() {}

    public ItemOrden(String codigoArticulo, String color, String talle, Integer cantidadSolicitada, OrdenDeCompra ordenDeCompra) {
        this.codigoArticulo = codigoArticulo;
        this.color = color;
        this.talle = talle;
        this.cantidadSolicitada = cantidadSolicitada;
        this.ordenDeCompra = ordenDeCompra;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public OrdenDeCompra getOrdenDeCompra() {
        return ordenDeCompra;
    }

    public void setOrdenDeCompra(OrdenDeCompra ordenDeCompra) {
        this.ordenDeCompra = ordenDeCompra;
    }
}
