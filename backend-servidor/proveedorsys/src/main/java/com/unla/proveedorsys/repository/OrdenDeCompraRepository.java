package com.unla.proveedorsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unla.proveedorsys.enums.EstadoOrden;
import com.unla.proveedorsys.model.OrdenDeCompra;

@Repository
public interface OrdenDeCompraRepository extends JpaRepository<OrdenDeCompra, Long> {
    
	List<OrdenDeCompra> findByTiendaId(Long tiendaId);

	OrdenDeCompra findByCodigoArticulo(String codigoArticulo);
	
	@Query("SELECT o FROM OrdenDeCompra o WHERE o.estado = :estado AND o.id_orden_despacho IS NULL AND o.codigoArticulo = :codigoArticulo")
    List<OrdenDeCompra> findOrdenesAceptadasSinDespachoPorCodigoArticulo(
        @Param("estado") EstadoOrden estado,
        @Param("codigoArticulo") String codigoArticulo
    );
    
}