package com.unla.stockearte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unla.stockearte.model.Catalogo;
import com.unla.stockearte.model.Producto;

@Repository
public interface CatalogoRepository extends JpaRepository<Catalogo, Long> {
	List<Catalogo> findByTienda_Id(Long tiendaId);

	@Query("SELECT cp.producto FROM CatalogoProducto cp WHERE cp.catalogo.id = :catalogoId")
	List<Producto> findProductosByCatalogoId(Long catalogoId);
}
