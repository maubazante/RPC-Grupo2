package com.unla.stockearte.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.stockearte.model.Catalogo;
import com.unla.stockearte.repository.CatalogoRepository;

@Service
public class CatalogoService {

	@Autowired
	private CatalogoRepository catalogoRepository;

	public List<Catalogo> getAllCatalogos() {
		return catalogoRepository.findAll();
	}

	public Optional<Catalogo> getCatalogoById(Long id) {
		return catalogoRepository.findById(id);
	}

	public Catalogo createCatalogo(Catalogo catalogo) {
		return catalogoRepository.save(catalogo);
	}

	public Catalogo updateCatalogo(Long id, Catalogo catalogo) {
		catalogo.setId(id);
		return catalogoRepository.save(catalogo);
	}

	public void deleteCatalogo(Long id) {
		catalogoRepository.deleteById(id);
	}

	public List<Catalogo> getCatalogosByTienda(Long tiendaId) {
		return catalogoRepository.findByTienda_Id(tiendaId);
	}
}
