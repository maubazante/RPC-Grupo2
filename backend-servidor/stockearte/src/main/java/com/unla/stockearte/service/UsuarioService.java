package com.unla.stockearte.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.unla.stockearte.model.Rol;
import com.unla.stockearte.model.Tienda;
import com.unla.stockearte.model.Usuario;
import com.unla.stockearte.repository.TiendaRepository;
import com.unla.stockearte.repository.UsuarioRepository;
import com.usuario.grpc.CreateUsuarioRequest;
import com.usuario.grpc.CreateUsuarioResponse;
import com.usuario.grpc.DeleteUsuarioRequest;
import com.usuario.grpc.DeleteUsuarioResponse;
import com.usuario.grpc.ModifyUsuarioRequest;
import com.usuario.grpc.ModifyUsuarioResponse;
import com.usuario.grpc.UsuarioServiceGrpc.UsuarioServiceImplBase;

import io.grpc.stub.StreamObserver;

@Service(UsuarioService.BEAN_NAME)
public class UsuarioService extends UsuarioServiceImplBase {

	public static final String BEAN_NAME = "UsuarioService";

	@Autowired()
	private UsuarioRepository usuarioRepository;

	@Autowired()
	private TiendaRepository tiendaRepository;

	@Transactional(readOnly = false, rollbackForClassName = { "java.lang.Throwable",
			"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	@Override
	public void createUsuario(CreateUsuarioRequest request, StreamObserver<CreateUsuarioResponse> responseObserver) {
		Usuario usuario = new Usuario();
		usuario.setApellido(request.getUsuario().getApellido());
		usuario.setNombre(request.getUsuario().getNombre());
		usuario.setPassword(request.getUsuario().getPassword());
		usuario.setUsername(request.getUsuario().getUsername());
		usuario.setRol(Rol.fromValue(request.getUsuario().getRol()));

		Optional<Tienda> tienda = getTiendaRepository().findById(request.getUsuario().getTiendaId());

		if (tienda.isPresent()) {
			usuario.setTienda(tienda.get());
			tienda.get().setUsuario(usuario);
		}

		getUsuarioRepository().save(usuario);

		CreateUsuarioResponse response = CreateUsuarioResponse.newBuilder().setMessage("Cuenta creada con exito")
				.build();

		responseObserver.onNext(response);
		responseObserver.onCompleted();

	}

	@Transactional(readOnly = false, rollbackForClassName = { "java.lang.Throwable",
			"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	@Override
	public void deleteUsuario(DeleteUsuarioRequest request, StreamObserver<DeleteUsuarioResponse> responseObserver) {
		Optional<Usuario> usuario = getUsuarioRepository().findById(request.getUsuarioId());
		DeleteUsuarioResponse response = null;

		if (usuario.isPresent()) {
			usuario.get().setHabilitado(false);
			getUsuarioRepository().save(usuario.get());

			response = DeleteUsuarioResponse.newBuilder()
					.setMessage("Usuario con id " + usuario.get().getId() + " eliminado exitosamente").build();
		}

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Transactional(readOnly = false, rollbackForClassName = { "java.lang.Throwable",
			"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	@Override
	public void modifyUsuario(ModifyUsuarioRequest request, StreamObserver<ModifyUsuarioResponse> responseObserver) {
		Optional<Usuario> usuario = getUsuarioRepository().findById(request.getUsuario().getId());
		ModifyUsuarioResponse response = null;
		
		if(usuario.isPresent()) {
			usuario.get().setApellido(request.getUsuario().getApellido());
			usuario.get().setHabilitado(request.getUsuario().getHabilitado());
			usuario.get().setNombre(request.getUsuario().getNombre());
			usuario.get().setPassword(request.getUsuario().getPassword());
			usuario.get().setUsername(request.getUsuario().getUsername());
			usuario.get().setRol(Rol.fromValue(request.getUsuario().getRol()));
			
			Optional<Tienda> tienda = getTiendaRepository().findById(request.getUsuario().getTiendaId());
			
			if (tienda.isPresent()) {
				usuario.get().setTienda(tienda.get());
				tienda.get().setUsuario(usuario.get());
			}
			
			getUsuarioRepository().save(usuario.get());
			
			response = ModifyUsuarioResponse.newBuilder().setMessage("Usuario con id " + request.getUsuario().getId() + " modificado con exito")
					.build();
			
		}

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	public UsuarioRepository getUsuarioRepository() {
		return usuarioRepository;
	}

	public TiendaRepository getTiendaRepository() {
		return tiendaRepository;
	}

}