package com.unla.stockearte.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.unla.stockearte.model.Rol;
import com.unla.stockearte.model.Tienda;
import com.unla.stockearte.model.Usuario;
import com.unla.stockearte.repository.TiendaRepository;
import com.unla.stockearte.repository.UsuarioRepository;
import com.usuario.grpc.CreateUsuarioRequest;
import com.usuario.grpc.CreateUsuarioResponse;
import com.usuario.grpc.DeleteUsuarioRequest;
import com.usuario.grpc.DeleteUsuarioResponse;
import com.usuario.grpc.FindUsuariosRequest;
import com.usuario.grpc.FindUsuariosResponse;
import com.usuario.grpc.GetUsuariosRequest;
import com.usuario.grpc.GetUsuariosResponse;
import com.usuario.grpc.ModifyUsuarioRequest;
import com.usuario.grpc.ModifyUsuarioResponse;

import com.usuario.grpc.UserLoginRequest;
import com.usuario.grpc.UserLoginResponse;
import com.usuario.grpc.UsuarioServiceGrpc.UsuarioServiceImplBase;

import io.grpc.stub.StreamObserver;

@Service(UsuarioService.BEAN_NAME)
public class UsuarioService extends UsuarioServiceImplBase {

	public static final String BEAN_NAME = "UsuarioService";

	@Autowired()
	private UsuarioRepository usuarioRepository;

	@Autowired()
	private TiendaRepository tiendaRepository;

	// ==========================
	// CONVERSION
	// ==========================

	private com.usuario.grpc.Usuario convertToProtoUsuario(Usuario usuario) {
		com.usuario.grpc.Usuario.Builder protoUsuarioBuilder = com.usuario.grpc.Usuario.newBuilder()
				.setId(usuario.getId())
				.setNombre(usuario.getNombre())
				.setApellido(usuario.getApellido())
				.setUsername(usuario.getUsername())
				.setPassword(usuario.getPassword())
				.setRol(usuario.getRol().toString())
				.setHabilitado(usuario.isHabilitado());

		// Verifica si el usuario tiene una tienda asignada
		if (usuario.getTienda() != null) {
			protoUsuarioBuilder.setTiendaId(usuario.getTienda().getId());
			protoUsuarioBuilder.setTiendaCodigo(usuario.getTienda().getCodigo());
		}

		return protoUsuarioBuilder.build();
	}

	// ==========================
	// CRUD
	// ==========================

	@Transactional(readOnly = false, rollbackForClassName = { "java.lang.Throwable",
			"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	@Override
	public void createUsuario(CreateUsuarioRequest request, StreamObserver<CreateUsuarioResponse> responseObserver) {
		CreateUsuarioResponse response = null;

		if (getUsuarioRepository().existsByIdAndRol(request.getUsuario().getIdUserAdmin(), Rol.ADMIN)) {
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

			response = CreateUsuarioResponse.newBuilder().setMessage("Cuenta creada con exito").build();
		} else {
			response = CreateUsuarioResponse.newBuilder().setMessage(
					"Error al crear la cuenta: el usuario autenticado no existe o carece de los permisos necesarios para registrar nuevos usuarios.")
					.build();
		}

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

		if (getUsuarioRepository().existsByIdAndRol(request.getUsuario().getIdUserAdmin(), Rol.ADMIN)) {
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
						.setMessage("Usuario con id " + request.getUsuario().getId() + " modificado con exito").build();
			}

		} else {
			response = ModifyUsuarioResponse.newBuilder()
					.setMessage(
							"Error al modificar la cuenta: el usuario autenticado no existe o carece de los permisos necesarios para modificar usuarios.")
					.build();
		}

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	// ==========================
	// BUSQUEDA
	// ==========================

	@Transactional(readOnly = true)
	public Optional<Set<Usuario>> findUsuarios(String username, Long tiendaId) {
		// Solo disponible para usuarios de casa central
		Optional<Usuario> optionalUsuario = usuarioRepository.findByUsername(username);
		if (optionalUsuario.isPresent() && !optionalUsuario.get().esDeCasaCentral())
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

	@Transactional(readOnly = true)
	@Override
	public void findUsuarios(FindUsuariosRequest request, StreamObserver<FindUsuariosResponse> responseObserver) {
		String username = request.getUsername();
		Long tiendaId = request.getTiendaId();

		Optional<Set<Usuario>> usuariosOpt = findUsuarios(username, tiendaId);

		FindUsuariosResponse.Builder responseBuilder = FindUsuariosResponse.newBuilder();
		usuariosOpt.ifPresent(usuarios -> {
			for (Usuario usuario : usuarios) {
				responseBuilder.addUsuarios(convertToProtoUsuario(usuario)); // Aquí usas el convert
			}
		});

		responseObserver.onNext(responseBuilder.build());
		responseObserver.onCompleted();
	}

	// ==========================
	// GETTERS
	// ==========================

	@Transactional(readOnly = true)
	@Override
	public void getUsuarios(GetUsuariosRequest request, StreamObserver<GetUsuariosResponse> responseObserver) {
		// Verifica si se deben traer solo los usuarios habilitados
		Boolean habilitados = request.hasHabilitados() ? request.getHabilitados() : null; // Verifica si se ha establecido

		System.out.println("¿Solo habilitados? " + habilitados);

		// Busca los usuarios según si se filtran habilitados o no
		List<Usuario> usuarios;
		if (habilitados != null) {
			usuarios = usuarioRepository.findByHabilitado(habilitados); // Solo usuarios habilitados o deshabilitados
		} else {
			usuarios = usuarioRepository.findAll(); // Todos los usuarios
		}

		System.out.println("Usuarios encontrados: ");

		// Construye la respuesta
		GetUsuariosResponse.Builder responseBuilder = GetUsuariosResponse.newBuilder();

		System.out.println("Usuarios encontrados: ");
		// Agrega los usuarios a la respuesta
		for (Usuario usuario : usuarios) {
			System.out.println(usuario);
			responseBuilder.addUsuarios(convertToProtoUsuario(usuario));
		}

		responseObserver.onNext(responseBuilder.build());
		responseObserver.onCompleted();
	}

	@Transactional(readOnly = true, rollbackForClassName = { "java.lang.Throwable",
			"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	public void loginUsuario(UserLoginRequest request, StreamObserver<UserLoginResponse> responseObserver) {
		Optional<Usuario> user = getUsuarioRepository().findByUsernameAndPassword(request.getUserLogin().getUsername(),
				request.getUserLogin().getPassword());
		UserLoginResponse response = null;
		if (user.isPresent()) {
			response = UserLoginResponse.newBuilder()
					.setPassword(user.get().getPassword())
					.setUsername(user.get().getUsername())
					.setRol(user.get().getRol().getValue())
					.setUserId(user.get().getId())
					.setTiendaId(user.get().getTienda().getId())
					.build();
		} else {
			response = UserLoginResponse.newBuilder()
					.setErrorMessage("El usuario ingresado no existe en la base de datos.")
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
    public List<String> procesarArchivoCSV(MultipartFile archivo) throws IOException {
        List<String> errores = new ArrayList<>();
        List<Usuario> usuariosARegistrar = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(archivo.getInputStream()))) {
            String linea;
            int numeroLinea = 1;

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(";");

                if (datos.length != 5) {
                    errores.add("Línea " + numeroLinea + ": Formato incorrecto.");
                    numeroLinea++;
                    continue;
                }

                String username = datos[0];
                String password = datos[1];
                String nombre = datos[2];
                String apellido = datos[3];
                Long codigoTienda;

                // Validar que los campos no estén vacíos
                if (username.isEmpty() || password.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || datos[4].isEmpty()) {
                    errores.add("Línea " + numeroLinea + ": Campos vacíos.");
                    numeroLinea++;
                    continue;
                }

                try {
                    codigoTienda = Long.parseLong(datos[4]);
                } catch (NumberFormatException e) {
                    errores.add("Línea " + numeroLinea + ": Código de tienda inválido.");
                    numeroLinea++;
                    continue;
                }

                // Validar duplicidad del usuario
                if (usuarioRepository.findByUsername(username).isPresent()) {
                    errores.add("Línea " + numeroLinea + ": Usuario duplicado.");
                    numeroLinea++;
                    continue;
                }

                // Validar existencia y estado de la tienda
                Tienda tienda = tiendaRepository.findById(codigoTienda).orElse(null);
                if (tienda == null) {
                    errores.add("Línea " + numeroLinea + ": Código de tienda no encontrado.");
                    numeroLinea++;
                    continue;
                }

                if (!tienda.isHabilitada()) {
                    errores.add("Línea " + numeroLinea + ": La tienda está deshabilitada.");
                    numeroLinea++;
                    continue;
                }

                // Crear usuario y agregar a la lista
                Usuario usuario = new Usuario(username, password, nombre, apellido, Rol.STOREMANAGER, tienda);
                usuariosARegistrar.add(usuario);

                numeroLinea++;
            }
        }

        // Guardar los usuarios válidos en la base de datos
        usuarioRepository.saveAll(usuariosARegistrar);

        return errores;
    }
}

