package com.unla.stockearte.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unla.stockearte.model.Novedad;

public interface NovedadRepository extends JpaRepository<Novedad, Long> {

	Optional<Novedad> findById(int id);
}