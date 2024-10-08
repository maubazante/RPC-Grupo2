package com.unla.proveedorsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.unla.proveedorsys.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

	@Query("SELECT CASE WHEN COALESCE(SUM(s.cantidad), 0) >= :cantidad THEN true ELSE false END " +
	           "FROM Stock s WHERE s.producto.codigo = :codigo")
	    Boolean tieneStockSuficiente(@Param("codigo") String codigo, @Param("cantidad") Integer cantidad);
}
