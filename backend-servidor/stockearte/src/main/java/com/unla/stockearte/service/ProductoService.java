package com.unla.stockearte.service;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.protobuf.ByteString;
import com.producto.grpc.FindProductosRequest;
import com.producto.grpc.FindProductosResponse;
import com.producto.grpc.CreateProductoRequest;
import com.producto.grpc.CreateProductoResponse;
import com.producto.grpc.DeleteProductoRequest;
import com.producto.grpc.DeleteProductoResponse;
import com.producto.grpc.ModifyProductoRequest;
import com.producto.grpc.ModifyProductoResponse;
import com.producto.grpc.ProductoServiceGrpc.ProductoServiceImplBase;
import com.producto.grpc.GetProductosRequest;
import com.producto.grpc.GetProductosResponse;
import com.unla.stockearte.helpers.Helper;
import com.unla.stockearte.model.Producto;
import com.unla.stockearte.model.Rol;
import com.unla.stockearte.model.Stock;
import com.unla.stockearte.model.StockId;
import com.unla.stockearte.model.Tienda;
import com.unla.stockearte.model.Usuario;
import com.unla.stockearte.repository.ProductoRepository;
import com.unla.stockearte.repository.StockRepository;
import com.unla.stockearte.repository.TiendaRepository;
import com.unla.stockearte.repository.UsuarioRepository;

import io.grpc.stub.StreamObserver;

@Service(ProductoService.BEAN_NAME)
public class ProductoService extends ProductoServiceImplBase {

	public static final String BEAN_NAME = "ProductoService";

	@Autowired()
	ProductoRepository productoRepository;

	@Autowired()
	TiendaRepository tiendaRepository;

	@Autowired()
	StockRepository stockRepository;

	@Autowired()
	UsuarioRepository usuarioRepository;

	// ==========================
	// CONVERSION
	// ==========================

	public com.producto.grpc.Producto convertToProtoProducto(Producto producto) {
		com.producto.grpc.Producto.Builder protoProductoBuilder = com.producto.grpc.Producto.newBuilder()
				.setId(producto.getId())
				.setNombre(producto.getNombre())
				.setCodigo(producto.getCodigo())
				.setColor(producto.getColor())
				.setTalle(producto.getTalle())
				.setCantidad(producto.getCantidad())
				.setHabilitado(producto.isHabilitado());

		if (producto.getFoto() != null) {
			protoProductoBuilder.setFoto(producto.getFoto());
		}

		return protoProductoBuilder.build();
	}

	// ==========================
	// CRUD
	// ==========================

	@Transactional(readOnly = false, rollbackForClassName = { "java.lang.Throwable",
			"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	@Override
	public void createProducto(CreateProductoRequest request, StreamObserver<CreateProductoResponse> responseObserver) {

		CreateProductoResponse response = null;
		if (getUsuarioRepository().existsByIdAndRol(request.getProducto().getIdUserAdmin(), Rol.ADMIN)) {
			Producto producto = new Producto();
			producto.setCodigo(Helper.generarCadenaAleatoria());
			producto.setColor(request.getProducto().getColor());
			producto.setFoto(request.getProducto().getFoto());
			producto.setNombre(request.getProducto().getNombre());
			producto.setTalle(request.getProducto().getTalle());
			producto.setCantidad((int) request.getProducto().getCantidad());

			getProductoRepository().save(producto);

			response = CreateProductoResponse.newBuilder()
					.setMessage("Producto con codigo " + producto.getCodigo() + " creado exitosamente").build();
		} else {
			response = CreateProductoResponse.newBuilder()
					.setMessage(
							"Error al crear el producto: el usuario autenticado no existe o carece de los permisos necesarios para crear productos.")
					.build();
		}

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Transactional(readOnly = false, rollbackForClassName = { "java.lang.Throwable",
			"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	@Override
	public void deleteProducto(DeleteProductoRequest request, StreamObserver<DeleteProductoResponse> responseObserver) {
		Optional<Producto> producto = getProductoRepository().findById(request.getProductoId());
		DeleteProductoResponse response = null;

		if (producto.isPresent()) {
			producto.get().setHabilitado(false);
			getProductoRepository().save(producto.get());

			response = DeleteProductoResponse.newBuilder()
					.setMessage("Producto con id " + request.getProductoId() + " eliminado exitosamente").build();
		}

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Transactional(readOnly = false, rollbackForClassName = { "java.lang.Throwable",
			"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	@Override
	public void modifyProducto(ModifyProductoRequest request, StreamObserver<ModifyProductoResponse> responseObserver) {
		Optional<Producto> producto = getProductoRepository().findById(request.getProducto().getId());
		ModifyProductoResponse response = null;

		if (producto.isPresent()) {
			producto.get().setColor(request.getProducto().getColor());
			producto.get().setNombre(request.getProducto().getNombre());
			producto.get().setTalle(request.getProducto().getTalle());
			producto.get().setFoto(request.getProducto().getFoto());
			producto.get().setCantidad((int) request.getProducto().getCantidad());

			getProductoRepository().save(producto.get());

			response = ModifyProductoResponse.newBuilder()
					.setMessage("Producto con id " + request.getProducto().getId() + " modificado exitosamente")
					.build();
		}

		else
			response = ModifyProductoResponse.newBuilder().setMessage("Error - Producto no existe").build();

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	// ==========================
	// BUSQUEDA
	// ==========================

	@Transactional(readOnly = true)
	public Optional<Set<Producto>> findProductos(String nombre, String codigo, String talle, String color) {
		Set<Producto> productos = productoRepository
				.findByNombreContainingOrCodigoContainingOrTalleContainingOrColorContaining(
						nombre, codigo, talle, color);

		return productos.isEmpty() ? Optional.empty() : Optional.of(productos);
	}

	@Transactional(readOnly = true)
	@Override
	public void findProductos(FindProductosRequest request,
			StreamObserver<FindProductosResponse> responseObserver) {
		Optional<Set<Producto>> productos = findProductos(request.getNombre(), request.getCodigo(),
				request.getTalle(), request.getColor());
		FindProductosResponse.Builder responseBuilder = FindProductosResponse.newBuilder();

		if (productos.isPresent()) {
			for (Producto producto : productos.get()) {
				responseBuilder.addProductos(convertToProtoProducto(producto));
			}
		}

		responseObserver.onNext(responseBuilder.build());
		responseObserver.onCompleted();
	}

	// ==========================
	// GETTERS
	// ==========================

	@Transactional(readOnly = true)
	@Override
	public void getProductos(GetProductosRequest request, StreamObserver<GetProductosResponse> responseObserver) {
		String username = request.getUsername();
		Boolean habilitados = request.hasHabilitados() ? request.getHabilitados() : null;

		System.out.println("Usuario: " + username);
		System.out.println("¿Solo habilitados? " + habilitados);

		// Busca productos basados en el usuario y los filtros aplicados
		List<Producto> productos = buscarProductosPorUsuarioYHabilitacion(username, habilitados);

		// Construye la respuesta
		GetProductosResponse.Builder responseBuilder = GetProductosResponse.newBuilder();

		// Agrega los productos a la respuesta
		for (Producto producto : productos) {
			System.out.println(producto);
			responseBuilder.addProductos(convertToProtoProducto(producto));
		}

		responseObserver.onNext(responseBuilder.build());
		responseObserver.onCompleted();
	}

	// Método para buscar productos según el tipo de usuario y el filtro de
	// habilitación
	private List<Producto> buscarProductosPorUsuarioYHabilitacion(String username, Boolean habilitados) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findByUsername(username);

		if (usuarioOptional.isPresent()) {
			Usuario usuario = usuarioOptional.get();
			if (usuario.esDeCasaCentral()) {
				return buscarProductosDeCasaCentral(habilitados);
			} else {
				return buscarProductosDeTienda(usuario.getTienda(), habilitados);
			}
		} else {
			return new ArrayList<>(); // Devuelve lista vacía si no se encuentra el usuario
		}
	}

	// Método para buscar productos de la casa central
	private List<Producto> buscarProductosDeCasaCentral(Boolean habilitados) {
		if (habilitados != null) {
			return productoRepository.findByHabilitado(habilitados); // Filtra por habilitados o deshabilitados
		} else {
			return productoRepository.findAll(); // Sin filtro, todos los productos
		}
	}

	// Método para buscar productos de una tienda específica
	private List<Producto> buscarProductosDeTienda(Tienda tienda, Boolean habilitados) {
		if (tienda == null) {
			return new ArrayList<>(); // Devuelve lista vacía si no hay tienda
		}

		if (habilitados != null) {
			return productoRepository.findByTiendaIdAndHabilitado(tienda.getId(), habilitados); // Filtra por
																								// habilitados
		} else {
			return productoRepository.findByTiendaId(tienda.getId()); // Todos los productos de su tienda
		}
	}

	public ProductoRepository getProductoRepository() {
		return productoRepository;
	}

	public TiendaRepository getTiendaRepository() {
		return tiendaRepository;
	}

	public StockRepository getStockRepository() {
		return stockRepository;
	}

	public UsuarioRepository getUsuarioRepository() {
		return usuarioRepository;
	}

}
