package com.unla.stockearte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.stockearte.model.Catalogo;

@Repository
public interface CatalogoRepository extends JpaRepository<Catalogo, Long> {
	List<Catalogo> findByTienda_Id(Long tiendaId);
}
