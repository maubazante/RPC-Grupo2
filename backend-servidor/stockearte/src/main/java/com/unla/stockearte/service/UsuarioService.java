package com.unla.stockearte.service;

import java.util.Set;
import java.util.HashSet;
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
import com.usuario.grpc.BuscarUsuariosRequest;
import com.usuario.grpc.BuscarUsuariosResponse;
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

	private com.usuario.grpc.Usuario convertToProtoUsuario(Usuario usuario) {
		return com.usuario.grpc.Usuario.newBuilder()
				.setId(usuario.getId())
				.setNombre(usuario.getNombre())
				.setApellido(usuario.getApellido())
				.setUsername(usuario.getUsername())
				.setPassword(usuario.getPassword())
				.setRol(usuario.getRol().toString())
				.setTiendaId(usuario.getTienda().getId())
				.setHabilitado(usuario.isHabilitado())
				.build();
	}

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

		if (tienda.isPresent())
			usuario.setTienda(tienda.get());

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

		if (usuario.isPresent()) {
			usuario.get().setApellido(request.getUsuario().getApellido());
			usuario.get().setHabilitado(request.getUsuario().getHabilitado());
			usuario.get().setNombre(request.getUsuario().getNombre());
			usuario.get().setPassword(request.getUsuario().getPassword());
			usuario.get().setUsername(request.getUsuario().getUsername());
			usuario.get().setRol(Rol.fromValue(request.getUsuario().getRol()));

			Optional<Tienda> tienda = getTiendaRepository().findById(request.getUsuario().getTiendaId());
			if (tienda.isPresent())
				usuario.get().setTienda(tienda.get());

			getUsuarioRepository().save(usuario.get());

			response = ModifyUsuarioResponse.newBuilder()
					.setMessage("Usuario con id " + request.getUsuario().getId() + " modificado con exito")
					.build();

		}

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Transactional(readOnly = true)
	public Optional<Set<Usuario>> buscarUsuarios(String username, Long tiendaId) {
		// Solo disponible para usuarios de casa central
		if (!esUsuarioDeCasaCentral(usuarioRepository.findByUsername(username).get().getTienda().getId()))
			throw new RuntimeException("Acceso no permitido: el usuario no pertenece a casa central.");

		Set<Usuario> usuarios;

		if (username != null && tiendaId != null) {
			usuarios = usuarioRepository.findByUsernameContainingAndTiendaId(username, tiendaId);
		} else if (username != null) {
			usuarios = usuarioRepository.findByUsernameContaining(username);
		} else if (tiendaId != null) {
			usuarios = usuarioRepository.findByTiendaId(tiendaId);
		} else {
			usuarios = new HashSet<>(usuarioRepository.findAll());
		}

		return usuarios.isEmpty() ? Optional.empty() : Optional.of(usuarios);
	}

	@Override
	public void buscarUsuarios(BuscarUsuariosRequest request, StreamObserver<BuscarUsuariosResponse> responseObserver) {
		String username = request.getUsername();
		Long tiendaId = request.getTiendaId();

		Optional<Set<Usuario>> usuariosOpt = buscarUsuarios(username, tiendaId);

		BuscarUsuariosResponse.Builder responseBuilder = BuscarUsuariosResponse.newBuilder();
		usuariosOpt.ifPresent(usuarios -> {
			for (Usuario usuario : usuarios) {
				responseBuilder.addUsuarios(convertToProtoUsuario(usuario)); // Aquí usas el convert
			}
		});

		responseObserver.onNext(responseBuilder.build());
		responseObserver.onCompleted();
	}

	public UsuarioRepository getUsuarioRepository() {
		return usuarioRepository;
	}

	public TiendaRepository getTiendaRepository() {
		return tiendaRepository;
	}

	public boolean esUsuarioDeCasaCentral(Long tiendaId) {
		Tienda tienda = tiendaRepository.findById(tiendaId).orElse(null);
		return tienda != null && tienda.getEsCasaCentral();
	}

}
