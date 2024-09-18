package com.unla.stockearte.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
	private UsuarioRepository usuarioRepository;

	@Autowired
	private StockRepository stockRepository;

	@Transactional(readOnly = false, rollbackForClassName = { "java.lang.Throwable",
			"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	@Override
	public void createTienda(CreateTiendaRequest request, StreamObserver<CreateTiendaResponse> responseObserver) {

		Tienda tienda = new Tienda();
		tienda.setCodigo(request.getTienda().getCodigo());
		tienda.setDireccion(request.getTienda().getDireccion());
		tienda.setCiudad(request.getTienda().getCiudad());
		tienda.setProvincia(request.getTienda().getProvincia());

		Optional<Usuario> usuario = usuarioRepository.findById(request.getTienda().getUsuarioId());
		if (usuario.isPresent()) {
			tienda.setUsuario(usuario.get());
		}

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
			tienda.setHabilitado(false);
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
			tienda.setHabilitado(request.getTienda().getHabilitada());
			tienda.setCiudad(request.getTienda().getCiudad());
			tienda.setCodigo(request.getTienda().getCodigo());
			tienda.setDireccion(request.getTienda().getDireccion());
			tienda.setProvincia(request.getTienda().getProvincia());

			Optional<Usuario> usuario = usuarioRepository.findById(request.getTienda().getUsuarioId());
			if (usuario.isPresent()) {
				tienda.setUsuario(usuario.get());
			}

			// TODO: Ver el tema de la asignacion de productos

			getTiendaRepository().save(tienda);

			response = ModifyTiendaResponse.newBuilder()
					.setMessage("Tienda con codigo " + request.getTienda().getCodigo() + " modificada exitosamente")
					.build();
		}

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	public TiendaRepository getTiendaRepository() {
		return tiendaRepository;
	}

	public UsuarioRepository getUsuarioRepository() {
		return usuarioRepository;
	}

	public StockRepository getStockRepository() {
		return stockRepository;
	}

}