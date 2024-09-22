package com.unla.stockearte.service;

import java.util.Set;
import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tienda.grpc.BuscarTiendasRequest;
import com.tienda.grpc.BuscarTiendasResponse;
import com.tienda.grpc.CreateTiendaRequest;
import com.tienda.grpc.CreateTiendaResponse;
import com.tienda.grpc.DeleteTiendaRequest;
import com.tienda.grpc.DeleteTiendaResponse;
import com.tienda.grpc.ModifyTiendaRequest;
import com.tienda.grpc.ModifyTiendaResponse;
import com.tienda.grpc.TiendaServiceGrpc.TiendaServiceImplBase;
import com.unla.stockearte.model.Tienda;
import com.unla.stockearte.model.Usuario;
import com.unla.stockearte.repository.StockRepository;
import com.unla.stockearte.repository.TiendaRepository;

import com.unla.stockearte.repository.UsuarioRepository;

import io.grpc.stub.StreamObserver;

@Service(TiendaService.BEAN_NAME)
public class TiendaService extends TiendaServiceImplBase {

	public final static String BEAN_NAME = "TiendaService";

	@Autowired
	private TiendaRepository tiendaRepository;

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioService usuarioService;

	private com.tienda.grpc.Tienda convertToProtoTienda(Tienda tienda) {
		com.tienda.grpc.Tienda.Builder tiendaBuilder = com.tienda.grpc.Tienda.newBuilder()
				.setId(tienda.getId())
				.setCodigo(tienda.getCodigo())
				.setDireccion(tienda.getDireccion())
				.setCiudad(tienda.getCiudad())
				.setProvincia(tienda.getProvincia())
				.setHabilitada(tienda.isHabilitada())
				.setEsCasaCentral(tienda.getEsCasaCentral());

		/*
		 * // Convertir la lista de Productos de Tienda a gRPC.
		 * if (tienda.getProductos() != null) {
		 * for (Stock producto : tienda.getProductos()) {
		 * tiendaBuilder.addProductos(convertToProtoStock(producto)); // Suponiendo que
		 * tienes un método para
		 * // convertir Stock a gRPC.
		 * }
		 * }
		 */

		return tiendaBuilder.build();
	}

	@Transactional(readOnly = false, rollbackForClassName = { "java.lang.Throwable",
			"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	@Override
	public void createTienda(CreateTiendaRequest request, StreamObserver<CreateTiendaResponse> responseObserver) {

		Tienda tienda = new Tienda();
		tienda.setCodigo(request.getTienda().getCodigo());
		tienda.setDireccion(request.getTienda().getDireccion());
		tienda.setCiudad(request.getTienda().getCiudad());
		tienda.setProvincia(request.getTienda().getProvincia());

		getTiendaRepository().save(tienda);

		CreateTiendaResponse response = CreateTiendaResponse.newBuilder()
				.setMessage("Tienda creada con codigo " + tienda.getCodigo() + " con exito").build();

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Transactional(readOnly = false, rollbackForClassName = { "java.lang.Throwable",
			"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	@Override
	public void deleteTienda(DeleteTiendaRequest request, StreamObserver<DeleteTiendaResponse> responseObserver) {
		Tienda tienda = getTiendaRepository().findByCodigo(request.getCodigo());
		DeleteTiendaResponse response = null;

		if (tienda != null) {
			tienda.setHabilitada(false);
			getTiendaRepository().save(tienda);

			response = DeleteTiendaResponse.newBuilder()
					.setMessage("Tienda con codigo " + request.getCodigo() + " eliminado exitosamente").build();
		}
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Transactional(readOnly = false, rollbackForClassName = { "java.lang.Throwable",
			"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	@Override
	public void modifyTienda(ModifyTiendaRequest request, StreamObserver<ModifyTiendaResponse> responseObserver) {
		Tienda tienda = getTiendaRepository().findByCodigo(request.getTienda().getCodigo());
		ModifyTiendaResponse response = null;

		if (tienda != null) {
			tienda.setHabilitada(request.getTienda().getHabilitada());
			tienda.setCiudad(request.getTienda().getCiudad());
			tienda.setCodigo(request.getTienda().getCodigo());
			tienda.setDireccion(request.getTienda().getDireccion());
			tienda.setProvincia(request.getTienda().getProvincia());

			// TODO: Ver el tema de la asignacion de productos

			getTiendaRepository().save(tienda);

			response = ModifyTiendaResponse.newBuilder()
					.setMessage("Tienda con codigo " + request.getTienda().getCodigo() + " modificada exitosamente")
					.build();
		}

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Transactional(readOnly = true)
	public Tienda buscarTienda(String codigo) {
		return tiendaRepository.findByCodigo(codigo);
	}

	@Transactional(readOnly = true)
	public Optional<Set<Tienda>> buscarTiendas(String codigo, Boolean habilitada, String username) {
		// Solo disponible para usuarios de casa central
		Usuario usuario = usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Acceso denegado: usuario no encontrado."));

		if (usuario.getTienda() == null || !usuarioService.esUsuarioDeCasaCentral(usuario.getTienda().getId()))
			throw new RuntimeException("Acceso denegado: solo usuarios de casa central pueden realizar esta acción.");

		Set<Tienda> resultado = new HashSet<>();

		if (codigo != null && habilitada != null) {
			resultado.addAll(tiendaRepository.findByCodigoAndHabilitada(codigo, habilitada));
		} else if (codigo != null) {
			Tienda tienda = buscarTienda(codigo);
			if (tienda != null)
				resultado.add(tienda);
		} else if (habilitada != null) {
			resultado = tiendaRepository.findByHabilitada(habilitada);
		} else {
			resultado.addAll(tiendaRepository.findAll());
		}

		return resultado.isEmpty() ? Optional.empty() : Optional.of(resultado);
	}

	@Transactional(readOnly = true)
	@Override
	public void buscarTiendas(BuscarTiendasRequest request, StreamObserver<BuscarTiendasResponse> responseObserver) {
		// Obtener los parámetros del request
		String codigo = request.getCodigo().isEmpty() ? null : request.getCodigo();
		Boolean habilitada = request.getHabilitada();
		String username = request.getUsername();

		try {
			Optional<Set<Tienda>> tiendasOptional = buscarTiendas(codigo, habilitada, username);

			// Crear la respuesta gRPC
			BuscarTiendasResponse.Builder responseBuilder = BuscarTiendasResponse.newBuilder();

			if (tiendasOptional.isPresent()) {
				Set<Tienda> tiendas = tiendasOptional.get();
				for (Tienda tienda : tiendas) {
					com.tienda.grpc.Tienda grpcTienda = convertToProtoTienda(tienda);
					responseBuilder.addTiendas(grpcTienda);
				}
			}

			responseObserver.onNext(responseBuilder.build());
			responseObserver.onCompleted();

		} catch (Exception e) {
			responseObserver.onError(e);
		}
	}

	public TiendaRepository getTiendaRepository() {
		return tiendaRepository;
	}

	public StockRepository getStockRepository() {
		return stockRepository;
	}

}
