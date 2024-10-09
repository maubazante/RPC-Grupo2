package com.unla.stockearte.repository;


import java.util.Optional;

import java.util.List;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.stockearte.model.Tienda;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda, Long> {

	// Buscar tienda por código
	Tienda findByCodigo(String codigo);

	// Buscar tiendas habilitadas (habilitada == true o false)
	List<Tienda> findByHabilitada(Boolean habilitada);

	// Buscar tiendas por código y habilitación
	Set<Tienda> findByCodigoAndHabilitada(String codigo, Boolean habilitada);

	Optional<Tienda> findById(Integer tiendaId);
	// Método para traer todas las tiendas
	
	List<Tienda> findAll();

}
