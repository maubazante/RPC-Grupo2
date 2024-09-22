package com.unla.stockearte.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.stockearte.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Set<Producto> findByNombreContainingOrCodigoContainingOrTalleContainingOrColorContaining(
            String nombre, String codigo, String talle, String color);
}
