package com.unla.stockearte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.stockearte.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
