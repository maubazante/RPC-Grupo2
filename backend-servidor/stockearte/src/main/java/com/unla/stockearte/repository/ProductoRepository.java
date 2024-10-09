package com.unla.stockearte.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unla.stockearte.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Override
    List<Producto> findAll();

    @Query("SELECT p FROM Producto p JOIN Stock s ON p.id = s.producto.id JOIN Tienda t ON s.tienda.id = t.id WHERE t.id = :tiendaId")
    List<Producto> findByTiendaId(@Param("tiendaId") Long tiendaId);

    Set<Producto> findByNombreContainingOrCodigoContainingOrTalleContainingOrColorContaining(
            String nombre, String codigo, String talle, String color);

    // Método para encontrar productos habilitados
    List<Producto> findByHabilitado(boolean habilitado);

    // Método para encontrar productos por tienda y estado de habilitación
    @Query("SELECT p FROM Producto p JOIN Stock s ON p.id = s.producto.id JOIN Tienda t ON s.tienda.id = t.id WHERE t.id = :tiendaId AND p.habilitado = :habilitado")
    List<Producto> findByTiendaIdAndHabilitado(@Param("tiendaId") Long tiendaId,
            @Param("habilitado") boolean habilitado);

    // Método para buscar un producto por código
    Optional<Producto> findByCodigo(String codigo);
}
