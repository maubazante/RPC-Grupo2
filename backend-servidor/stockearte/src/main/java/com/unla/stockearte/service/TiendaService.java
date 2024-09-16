package com.unla.stockearte.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.grpc.CreateTiendaRequest;
import com.example.grpc.CreateTiendaResponse;
import com.example.grpc.DeleteTiendaRequest;
import com.example.grpc.DeleteTiendaResponse;
import com.example.grpc.TiendaServiceGrpc.TiendaServiceImplBase;
import com.unla.stockearte.model.Tienda;
import com.unla.stockearte.model.Usuario;
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

	@Transactional(readOnly = false, rollbackForClassName = { "java.lang.Throwable",
	"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	@Override
	public void createTienda(CreateTiendaRequest request, StreamObserver<CreateTiendaResponse> responseObserver) {

		Tienda tienda = new Tienda();
		tienda.setCodigo(request.getTienda().getCodigo());
		tienda.setDireccion(request.getTienda().getDireccion());
		tienda.setCiudad(request.getTienda().getCiudad());
		tienda.setProvincia(request.getTienda().getProvincia());
		tienda.setHabilitado(request.getTienda().getHabilitada());

		if (request.getTienda().getUsuarioId() != 0) {
			Optional<Usuario> usuario = usuarioRepository.findById(request.getTienda().getUsuarioId());
			if(usuario.isPresent()) {
				tienda.setUsuario(usuario.get());
			}
		}

		getTiendaRepository().save(tienda);

		CreateTiendaResponse response = CreateTiendaResponse.newBuilder()
				.setMessage("Tienda creada con codigo " + tienda.getCodigo() + " creada con exito").build();

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Override
	public void deleteTienda(DeleteTiendaRequest request, StreamObserver<DeleteTiendaResponse> responseObserver) {
		// TODO Auto-generated method stub
		super.deleteTienda(request, responseObserver);
	}

	public TiendaRepository getTiendaRepository() {
		return tiendaRepository;
	}

}
