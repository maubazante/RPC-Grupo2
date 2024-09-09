package com.unla.stockearte.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "stock")
public class Stock {

    @EmbeddedId
    private StockId id;

    @ManyToOne
    @MapsId("tiendaId")
    @JoinColumn(name = "tienda_id")
    private Tienda tienda;

    @ManyToOne
    @MapsId("productoId")
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @Column(nullable = false)
    private Integer stock = 0;

}
