package com.unla.stockearte.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unla.stockearte.model.Novedad;

public interface NovedadRepository extends JpaRepository<Novedad, Long> {
}