package com.unla.stockearte.repository;

import java.util.Set;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unla.stockearte.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findAll();

    @Query("SELECT p FROM Producto p JOIN Stock s ON p.id = s.producto.id JOIN Tienda t ON s.tienda.id = t.id WHERE t.id = :tiendaId")
    List<Producto> findByTiendaId(@Param("tiendaId") Long tiendaId);

    Set<Producto> findByNombreContainingOrCodigoContainingOrTalleContainingOrColorContaining(
            String nombre, String codigo, String talle, String color);
}
