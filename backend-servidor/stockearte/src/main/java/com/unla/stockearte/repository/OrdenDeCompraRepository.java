package com.unla.stockearte.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unla.stockearte.dto.InformeOrdenCompraDTO;
import com.unla.stockearte.enums.EstadoOrden;
import com.unla.stockearte.model.OrdenDeCompra;
import com.unla.stockearte.model.Tienda;

@Repository
public interface OrdenDeCompraRepository extends JpaRepository<OrdenDeCompra, Long> {
	
	@Query("SELECT new com.unla.stockearte.dto.InformeOrdenCompraDTO(o.codigoArticulo, o.estado, o.tienda, SUM(o.cantidadSolicitada)) " +
	           "FROM OrdenDeCompra o " +
	           "WHERE (:codigoArticulo IS NULL OR o.codigoArticulo = :codigoArticulo) " +
	           "AND (:fechaInicio IS NULL OR o.fechaSolicitud >= :fechaInicio) " +
	           "AND (:fechaFin IS NULL OR o.fechaSolicitud <= :fechaFin) " +
	           "AND (:estado IS NULL OR o.estado = :estado) " +
	           "AND (:tienda IS NULL OR o.tienda = :tienda) " +
	           "GROUP BY o.codigoArticulo, o.estado, o.tienda")
	    List<InformeOrdenCompraDTO> obtenerInformeOrdenesDeCompra(
	        @Param("codigoArticulo") String codigoArticulo,
	        @Param("fechaInicio") LocalDate fechaInicio,
	        @Param("fechaFin") LocalDate fechaFin,
	        @Param("estado") EstadoOrden estado,
	        @Param("tienda") Tienda tienda);
}
