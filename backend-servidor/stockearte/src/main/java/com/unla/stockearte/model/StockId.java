package com.unla.stockearte.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class StockId implements Serializable {

    @Column(name = "tienda_id")
    private Long tiendaId;

    @Column(name = "producto_id")
    private Long productoId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockId stockId = (StockId) o;
        return Objects.equals(tiendaId, stockId.tiendaId) && 
               Objects.equals(productoId, stockId.productoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tiendaId, productoId);
    }
}
