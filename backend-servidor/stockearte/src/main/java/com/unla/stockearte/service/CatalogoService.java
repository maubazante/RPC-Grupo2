package com.unla.stockearte.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional(readOnly = true, rollbackForClassName = { "java.lang.Throwable",
	"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	public List<Producto> obtenerProductosPorCatalogo(Long catalogoId) {
		return catalogoRepository.findProductosByCatalogoId(catalogoId);
	}

	@Transactional(readOnly = true, rollbackForClassName = { "java.lang.Throwable",
	"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	public List<Catalogo> getAllCatalogos(String username) {
		Usuario usuario = usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new UnauthorizedException("Usuario no encontrado"));

		// Verificar permisos del usuario
		if (!usuario.isHabilitado() || !isAuthorizedUser(username)) {
			throw new UnauthorizedException("El usuario no tiene permisos para ver los catálogos.");
		}

		Tienda tienda = usuario.getTienda();
		List<Catalogo> catalogos;

		// Si es casa central, traer todos los catálogos, sino solo los de su tienda
		if (tienda != null && tienda.getEsCasaCentral()) {
			catalogos = catalogoRepository.findAll();
		} else {
			Long tiendaId = tienda != null ? tienda.getId() : null;
			catalogos = catalogoRepository.findByTienda_Id(tiendaId);
		}

		catalogos.forEach(catalogo -> catalogo.getProductos().size());

		return catalogos;
	}

	public Optional<Catalogo> getCatalogoById(Long id, String username) {
		Usuario usuario = usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new UnauthorizedException("Usuario no encontrado"));

		// Verificar permisos del usuario
		if (!usuario.isHabilitado() || !isAuthorizedUser(username)) {
			throw new UnauthorizedException("El usuario no tiene permisos para ver el catálogo.");
		}

		Tienda tienda = usuario.getTienda();

		// Buscar el catálogo
		Optional<Catalogo> catalogoOpt = catalogoRepository.findById(id);

		// Verificar si el catálogo existe
		if (!catalogoOpt.isPresent()) {
			throw new NoSuchElementException("Catálogo no encontrado.");
		}

		Catalogo catalogo = catalogoOpt.get();

		// Verificar si el usuario tiene acceso al catálogo
		if (tienda != null && !tienda.getEsCasaCentral() && !catalogo.getTienda().getId().equals(tienda.getId())) {
			throw new UnauthorizedException("El usuario no tiene permisos para ver este catálogo.");
		}

		// Cargar productos para el catálogo
		catalogo.getProductos().size();

		return catalogoOpt;
	}

	@Transactional(readOnly = false, rollbackForClassName = { "java.lang.Throwable",
	"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	public Catalogo createCatalogo(CatalogoDTO catalogoDTO, String username) {
		Usuario usuario = usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new UnauthorizedException("Usuario no encontrado"));

		// Verificar permisos del usuario
		if (!usuario.isHabilitado() || !isAuthorizedUser(username)) {
			throw new UnauthorizedException("El usuario no tiene permisos para crear el catálogo.");
		}

		// Obtener la tienda del usuario
		Tienda tiendaUsuario = usuario.getTienda();

		// Verificar si el usuario puede crear un catálogo en la tienda
		Tienda tienda = tiendaRepository.findById(catalogoDTO.getTiendaId())
				.orElseThrow(() -> new ResourceNotFoundException("Tienda no encontrada"));

		// Permitir la creación si el usuario pertenece a la casa central o la tienda es
		// la misma
		if (tiendaUsuario != null && !tiendaUsuario.getEsCasaCentral()
				&& !tiendaUsuario.getId().equals(tienda.getId())) {
			throw new UnauthorizedException("El usuario no tiene permisos para crear un catálogo en esta tienda.");
		}

		Catalogo catalogo = new Catalogo();
		catalogo.setNombre(catalogoDTO.getNombre());
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

	@Transactional(readOnly = false, rollbackForClassName = { "java.lang.Throwable",
	"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	public Catalogo updateCatalogo(Long id, CatalogoDTO catalogoDTO, String username) {
		Usuario usuario = usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new UnauthorizedException("Usuario no encontrado"));

		if (!usuario.isHabilitado() || !isAuthorizedUser(username)) {
			throw new UnauthorizedException("El usuario no tiene permisos para actualizar el catálogo.");
		}

		Catalogo catalogoExistente = catalogoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Catálogo no encontrado"));

		Tienda tiendaUsuario = usuario.getTienda();

		if (tiendaUsuario != null && !tiendaUsuario.getEsCasaCentral()
				&& !catalogoExistente.getTienda().getId().equals(tiendaUsuario.getId())) {
			throw new UnauthorizedException("El usuario no tiene permisos para actualizar este catálogo.");
		}

		catalogoExistente.setNombre(catalogoDTO.getNombre());

		catalogoExistente.getProductos().clear();

		List<CatalogoProducto> catalogoProductos = new ArrayList<>();
		for (Long productoId : catalogoDTO.getProductoIds()) {
			Producto producto = productoRepository.findById(productoId)
					.orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + productoId));

			CatalogoProducto catalogoProducto = new CatalogoProducto();
			catalogoProducto.setCatalogo(catalogoExistente);
			catalogoProducto.setProducto(producto);
			catalogoProductos.add(catalogoProducto);
		}

		catalogoExistente.setCatalogoProductos(catalogoProductos);

		return catalogoRepository.save(catalogoExistente);
	}

	public void deleteCatalogo(Long id, String username) {
		Usuario usuario = usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new UnauthorizedException("Usuario no encontrado"));

		if (!usuario.isHabilitado() || !isAuthorizedUser(username)) {
			throw new UnauthorizedException("El usuario no tiene permisos para eliminar el catálogo.");
		}

		Catalogo catalogoExistente = catalogoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Catálogo no encontrado"));

		Tienda tiendaUsuario = usuario.getTienda();

		if (tiendaUsuario != null && !tiendaUsuario.getEsCasaCentral()
				&& !catalogoExistente.getTienda().getId().equals(tiendaUsuario.getId())) {
			throw new UnauthorizedException("El usuario no tiene permisos para eliminar este catálogo.");
		}

		catalogoRepository.deleteById(id);
	}

	private boolean isAuthorizedUser(String username) {
		Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
		return usuario.isPresent()
				&& (usuario.get().getRol() == Rol.ADMIN || usuario.get().getRol() == Rol.STOREMANAGER);
	}
	
	@Transactional(readOnly = true, rollbackForClassName = { "java.lang.Throwable",
	"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	public byte[] exportCatalogoPDF(Long id, String username) throws IOException {
		Catalogo catalogo = getCatalogoById(id, username)
				.orElseThrow(() -> new RuntimeException("Catálogo no encontrado"));

		Tienda tienda = catalogo.getTienda();

		Optional<Usuario> usuario = usuarioRepository.findByUsername(username);

		if (!usuario.isPresent()) {
			throw new RuntimeException("Acceso denegado: el usuario no encontrado.");
		}

		Optional<Tienda> tiendaUsuarioOpt = tiendaRepository.findById(usuario.get().getTienda().getId());

		if (!tiendaUsuarioOpt.isPresent()) {
			throw new RuntimeException("Acceso denegado: la tienda del usuario no encontrada.");
		}

		Tienda tiendaUsuario = tiendaUsuarioOpt.get();

		// Verificar si la tienda del usuario es la casa central o la misma tienda del
		// catálogo
		if (!isAuthorizedUser(username)
				|| (!tiendaUsuario.getEsCasaCentral() && !tiendaUsuario.getId().equals(tienda.getId()))) {
			throw new RuntimeException("Acceso denegado: el usuario no tiene permiso para acceder a este catálogo.");
		}

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
