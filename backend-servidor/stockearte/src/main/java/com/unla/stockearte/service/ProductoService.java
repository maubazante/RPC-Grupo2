package com.unla.stockearte.service;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.protobuf.ByteString;
import com.producto.grpc.BuscarProductosRequest;
import com.producto.grpc.BuscarProductosResponse;
import com.producto.grpc.CreateProductoRequest;
import com.producto.grpc.CreateProductoResponse;
import com.producto.grpc.DeleteProductoRequest;
import com.producto.grpc.DeleteProductoResponse;
import com.producto.grpc.ModifyProductoRequest;
import com.producto.grpc.ModifyProductoResponse;
import com.producto.grpc.ProductoProto;
import com.producto.grpc.ProductoServiceGrpc.ProductoServiceImplBase;

import com.unla.stockearte.helpers.Helper;
import com.unla.stockearte.model.Producto;
import com.unla.stockearte.model.Stock;
import com.unla.stockearte.model.StockId;
import com.unla.stockearte.model.Tienda;
import com.unla.stockearte.repository.ProductoRepository;
import com.unla.stockearte.repository.StockRepository;
import com.unla.stockearte.repository.TiendaRepository;

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

	public com.producto.grpc.Producto convertToProto(Producto producto) {
		return com.producto.grpc.Producto.newBuilder()
				.setId(producto.getId())
				.setNombre(producto.getNombre())
				.setCodigo(producto.getCodigo())
				.setFoto(ByteString.copyFrom(producto.getFoto()))
				.setColor(producto.getColor())
				.setTalle(producto.getTalle())
				.setHabilitado(producto.isHabilitado())
				.build();
	}

	@Transactional(readOnly = false, rollbackForClassName = { "java.lang.Throwable",
			"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	@Override
	public void createProducto(CreateProductoRequest request, StreamObserver<CreateProductoResponse> responseObserver) {
		Producto producto = new Producto();
		producto.setCodigo(Helper.generarCadenaAleatoria());
		producto.setColor(request.getProducto().getColor());
		producto.setFoto(request.getProducto().getFoto().toByteArray());
		producto.setNombre(request.getProducto().getNombre());
		producto.setTalle(request.getProducto().getTalle());

		getProductoRepository().save(producto);

		List<Stock> stockList = new ArrayList<>();
		for (Long tiendaid : request.getProducto().getTiendaIdsList()) {
			Optional<Tienda> tienda = getTiendaRepository().findById(tiendaid);
			if (tienda.isPresent()) {
				Stock stock = new Stock();
				StockId stockId = new StockId();

				stockId.setProductoId(producto.getId());
				stock.setProducto(producto);
				stock.setTienda(tienda.get());
				stockId.setTiendaId(tienda.get().getId());
				stock.setId(stockId);
				stock.setStock(Integer.valueOf(0));
				stockList.add(stock);
			}

		}

		getStockRepository().saveAll(stockList);

		CreateProductoResponse response = CreateProductoResponse.newBuilder()
				.setMessage("Producto con codigo " + producto.getCodigo() + " creado exitosamente").build();

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
			producto.get().setFoto(request.getProducto().getFoto().toByteArray());
			producto.get().setNombre(request.getProducto().getNombre());
			producto.get().setTalle(request.getProducto().getTalle());

			getProductoRepository().save(producto.get());

			response = ModifyProductoResponse.newBuilder()
					.setMessage("Producto con id " + request.getProducto().getId() + " modificado exitosamente")
					.build();
		}

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Transactional(readOnly = true)
	public Optional<Set<Producto>> buscarProductos(String nombre, String codigo, String talle, String color) {
		Set<Producto> productos = productoRepository
				.findByNombreContainingOrCodigoContainingOrTalleContainingOrColorContaining(
						nombre, codigo, talle, color);

		return productos.isEmpty() ? Optional.empty() : Optional.of(productos);
	}

	@Transactional(readOnly = true)
	@Override
	public void buscarProductos(BuscarProductosRequest request,
			StreamObserver<BuscarProductosResponse> responseObserver) {
		Optional<Set<Producto>> productos = buscarProductos(request.getNombre(), request.getCodigo(),
				request.getTalle(), request.getColor());
		BuscarProductosResponse.Builder responseBuilder = BuscarProductosResponse.newBuilder();

		if (productos.isPresent()) {
			for (Producto producto : productos.get()) {
				responseBuilder.addProductos(convertToProto(producto));
			}
		}

		responseObserver.onNext(responseBuilder.build());
		responseObserver.onCompleted();
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

}
