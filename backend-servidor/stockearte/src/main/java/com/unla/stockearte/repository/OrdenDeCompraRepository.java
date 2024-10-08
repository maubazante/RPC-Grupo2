package com.unla.stockearte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.unla.stockearte.model.OrdenDeCompra;

@Repository
public interface OrdenDeCompraRepository extends JpaRepository<OrdenDeCompra, Long> {
}
