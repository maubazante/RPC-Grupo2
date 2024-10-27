package com.unla.soapsys.response;

import java.io.Serializable;
import java.util.Objects;

public class StockId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long tiendaId;

    private Long productoId;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        StockId stockId = (StockId) o;
        return Objects.equals(tiendaId, stockId.tiendaId) &&
                Objects.equals(productoId, stockId.productoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tiendaId, productoId);
    }

    public Long getTiendaId() {
        return tiendaId;
    }

    public void setTiendaId(Long tiendaId) {
        this.tiendaId = tiendaId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

	public StockId(Long tiendaId, Long productoId) {
		super();
		this.tiendaId = tiendaId;
		this.productoId = productoId;
	}

	public StockId() {
		// TODO Auto-generated constructor stub
	}
}
