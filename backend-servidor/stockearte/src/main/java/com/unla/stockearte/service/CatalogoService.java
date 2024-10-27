package com.unla.stockearte.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.stockearte.helpers.Helper;
import com.unla.stockearte.helpers.Helper.UnauthorizedException;
import com.unla.stockearte.model.Catalogo;
import com.unla.stockearte.model.Producto;
import com.unla.stockearte.model.Rol;
import com.unla.stockearte.model.Usuario;
import com.unla.stockearte.repository.CatalogoRepository;
import com.unla.stockearte.repository.UsuarioRepository;

@Service
public class CatalogoService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CatalogoRepository catalogoRepository;

	public List<Producto> obtenerProductosPorCatalogo(Long catalogoId) {
		return catalogoRepository.findProductosByCatalogoId(catalogoId);
	}

	// TODO FILTRAR los catalogos segun la tienda del usuario o si es casa central
	// traer todos!!!
	// TODO FILTRAR los catalogos segun la tienda del usuario o si es casa central
	// traer todos!!!
	// TODO FILTRAR los catalogos segun la tienda del usuario o si es casa central
	// traer todos!!!
	// TODO FILTRAR los catalogos segun la tienda del usuario o si es casa central
	// traer todos!!!
	// TODO FILTRAR los catalogos segun la tienda del usuario o si es casa central
	// traer todos!!!
	// TODO FILTRAR los catalogos segun la tienda del usuario o si es casa central
	// traer todos!!!
	// TODO FILTRAR los catalogos segun la tienda del usuario o si es casa central
	// traer todos!!!
	public List<Catalogo> getAllCatalogos(String username, Long tiendaId) {
		if (!isAuthorizedUser(username)) {
			throw new UnauthorizedException("El usuario no tiene permisos para ver los catálogos.");
		}

		List<Catalogo> catalogos;

		if (tiendaId != null) {
			catalogos = catalogoRepository.findByTienda_Id(tiendaId);
		} else {
			catalogos = catalogoRepository.findAll();
		}

		// Aseguramos que los productos se carguen para cada catálogo
		catalogos.forEach(catalogo -> {
			catalogo.getProductos().size();
		});

		return catalogos;
	}

	public Optional<Catalogo> getCatalogoById(Long id, String username) {
		if (!isAuthorizedUser(username)) {
			throw new UnauthorizedException("El usuario no tiene permisos para ver el catálogo.");
		}
		return catalogoRepository.findById(id);
	}

	public Catalogo createCatalogo(Catalogo catalogo, String username) {
		if (!isAuthorizedUser(username)) {
			throw new UnauthorizedException("El usuario no tiene permisos para crear el catálogo.");
		}
		return catalogoRepository.save(catalogo);
	}

	public Catalogo updateCatalogo(Long id, Catalogo catalogo, String username) {
		if (!isAuthorizedUser(username)) {
			throw new UnauthorizedException("El usuario no tiene permisos para actualizar el catálogo.");
		}
		return catalogoRepository.save(catalogo);
	}

	public void deleteCatalogo(Long id, String username) {
		if (!isAuthorizedUser(username)) {
			throw new UnauthorizedException("El usuario no tiene permisos para eliminar el catálogo.");
		}
		catalogoRepository.deleteById(id);
	}

	private boolean isAuthorizedUser(String username) {
		Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
		return usuario.isPresent()
				&& (usuario.get().getRol() == Rol.ADMIN || usuario.get().getRol() == Rol.STOREMANAGER);
	}

	public byte[] exportCatalogoPDF(Long id, String username) throws IOException {
		Catalogo catalogo = getCatalogoById(id, username)
				.orElseThrow(() -> new RuntimeException("Catálogo no encontrado"));

		String contenido = "Catálogo: " + catalogo.getNombre();

		return Helper.generarPDF(contenido);
	}
}
