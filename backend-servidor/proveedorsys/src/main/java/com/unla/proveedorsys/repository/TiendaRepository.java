package com.unla.proveedorsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.stockearte.model.Tienda;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda, Long> {
    Tienda findByCodigo(String codigo);
}

