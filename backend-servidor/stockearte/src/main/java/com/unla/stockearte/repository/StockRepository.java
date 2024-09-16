package com.unla.stockearte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.stockearte.model.Stock;
import com.unla.stockearte.model.StockId;

@Repository
public interface StockRepository  extends JpaRepository<Stock, StockId>{

}
