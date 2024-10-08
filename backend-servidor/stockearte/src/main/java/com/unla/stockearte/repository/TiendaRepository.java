package com.unla.stockearte.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.stockearte.model.Tienda;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda, Long> {

	Tienda findByCodigo(String codigo);

	Set<Tienda> findByHabilitada(Boolean habilitada);

	Set<Tienda> findByCodigoAndHabilitada(String codigo, Boolean habilitada);

	Optional<Tienda> findById(Integer tiendaId);
}
