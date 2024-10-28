package com.unla.stockearte.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unla.stockearte.dto.InformeOrdenCompraDTO;
import com.unla.stockearte.enums.EstadoOrden;
import com.unla.stockearte.model.FiltroOrdenes;
import com.unla.stockearte.model.Tienda;

@Repository
public interface FiltroOrdenesRepository extends JpaRepository<FiltroOrdenes, Long> {

	List<FiltroOrdenes> findByFkUsuariosId(Long fkUsuariosId);
}
