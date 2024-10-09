package com.unla.stockearte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.stockearte.model.Producto;
import com.unla.stockearte.model.Stock;
import com.unla.stockearte.model.StockId;
import java.util.List;


@Repository
public interface StockRepository  extends JpaRepository<Stock, StockId>{
	
	List<Stock> findByProducto(Producto producto);
	
}
