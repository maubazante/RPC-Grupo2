package com.unla.stockearte.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.stockearte.dto.CatalogoDTO;
import com.unla.stockearte.helpers.ContenidoPDF;
import com.unla.stockearte.helpers.Helper.UnauthorizedException;
import com.unla.stockearte.helpers.HelperPDF;
import com.unla.stockearte.model.Catalogo;
import com.unla.stockearte.model.CatalogoProducto;
import com.unla.stockearte.model.Producto;
import com.unla.stockearte.model.Rol;
import com.unla.stockearte.model.Tienda;
import com.unla.stockearte.model.Usuario;
import com.unla.stockearte.repository.CatalogoRepository;
import com.unla.stockearte.repository.ProductoRepository;
import com.unla.stockearte.repository.TiendaRepository;
import com.unla.stockearte.repository.UsuarioRepository;

@Service
public class CatalogoService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CatalogoRepository catalogoRepository;

	@Autowired
	private TiendaRepository tiendaRepository;

	@Autowired
	private ProductoRepository productoRepository;

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

	public Catalogo createCatalogo(CatalogoDTO catalogoDTO, String username) {
		if (!isAuthorizedUser(username)) {
			throw new UnauthorizedException("El usuario no tiene permisos para crear el catálogo.");
		}

		Catalogo catalogo = new Catalogo();
		catalogo.setNombre(catalogoDTO.getNombre());

		Tienda tienda = tiendaRepository.findById(catalogoDTO.getTiendaId())
				.orElseThrow(() -> new ResourceNotFoundException("Tienda no encontrada"));
		catalogo.setTienda(tienda);

		List<CatalogoProducto> catalogoProductos = new ArrayList<>();
		for (Long productoId : catalogoDTO.getProductoIds()) {
			Producto producto = productoRepository.findById(productoId)
					.orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + productoId));

			CatalogoProducto catalogoProducto = new CatalogoProducto();
			catalogoProducto.setCatalogo(catalogo);
			catalogoProducto.setProducto(producto);
			catalogoProductos.add(catalogoProducto);
		}

		catalogo.setCatalogoProductos(catalogoProductos);
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
		Tienda tienda = catalogo.getTienda();
		List<Producto> productos = catalogo.getProductos();

		// Crear la lista de contenidos para el PDF
		List<ContenidoPDF> contenidos = new ArrayList<>();
		contenidos.add(new ContenidoPDF("Catálogo: " + catalogo.getNombre(), null));
		contenidos
				.add(new ContenidoPDF("Tienda: " + tienda.getCodigo() + "\nDirección: " + tienda.getDireccion(), null));

		for (Producto producto : productos) {
			String detallesProducto = String.format("%s, %s. Talle %s", producto.getCodigo(), producto.getNombre(),
					producto.getTalle());

			contenidos.add(new ContenidoPDF(detallesProducto, producto.getFoto()));
		}

		return HelperPDF.generarPDF(contenidos);
	}

}
