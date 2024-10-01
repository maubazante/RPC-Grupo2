package com.unla.stockearte.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tienda.grpc.FindTiendasRequest;
import com.tienda.grpc.FindTiendasResponse;
import com.tienda.grpc.CreateTiendaRequest;
import com.tienda.grpc.CreateTiendaResponse;
import com.tienda.grpc.DeleteTiendaRequest;
import com.tienda.grpc.DeleteTiendaResponse;
import com.tienda.grpc.ModifyTiendaRequest;
import com.tienda.grpc.ModifyTiendaResponse;
import com.tienda.grpc.GetTiendasRequest;
import com.tienda.grpc.GetTiendasResponse;
import com.tienda.grpc.TiendaServiceGrpc.TiendaServiceImplBase;
import com.unla.stockearte.model.ItemOrden;
import com.unla.stockearte.model.OrdenCompra;
import com.unla.stockearte.model.Rol;
import com.unla.stockearte.model.Tienda;
import com.unla.stockearte.model.Usuario;
import com.unla.stockearte.repository.OrdenCompraRepository;
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
	 private OrdenCompraRepository ordenCompraRepository;

	 @Autowired
	 private OrdenCompraProducer ordenCompraProducer;

	// ==========================
	// CONVERSION
	// ==========================

	private com.tienda.grpc.Tienda convertToProtoTienda(Tienda tienda) {
		com.tienda.grpc.Tienda.Builder tiendaBuilder = com.tienda.grpc.Tienda.newBuilder().setId(tienda.getId())
				.setCodigo(tienda.getCodigo()).setDireccion(tienda.getDireccion()).setCiudad(tienda.getCiudad())
				.setProvincia(tienda.getProvincia()).setHabilitada(tienda.isHabilitada())
				.setEsCasaCentral(tienda.getEsCasaCentral());

		/*
		 * // Convertir la lista de Productos de Tienda a gRPC. if
		 * (tienda.getProductos() != null) { for (Stock producto :
		 * tienda.getProductos()) {
		 * tiendaBuilder.addProductos(convertToProtoStock(producto)); // Suponiendo que
		 * tienes un método para // convertir Stock a gRPC. } }
		 */

		return tiendaBuilder.build();
	}

	// ==========================
	// CRUD
	// ==========================

	@Transactional(readOnly = false, rollbackForClassName = { "java.lang.Throwable",
			"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	@Override
	public void createTienda(CreateTiendaRequest request, StreamObserver<CreateTiendaResponse> responseObserver) {
		
		CreateTiendaResponse response = null;
		if(getUsuarioRepository().existsByIdAndRol(request.getTienda().getIdUserAdmin(), Rol.ADMIN)) {
			Tienda tienda = new Tienda();
			tienda.setCodigo(request.getTienda().getCodigo());
			tienda.setDireccion(request.getTienda().getDireccion());
			tienda.setCiudad(request.getTienda().getCiudad());
			tienda.setProvincia(request.getTienda().getProvincia());
			
			getTiendaRepository().save(tienda);
			
			response = CreateTiendaResponse.newBuilder()
					.setMessage("Tienda creada con codigo " + tienda.getCodigo() + " con exito").build();			
		} else {
			response = CreateTiendaResponse.newBuilder()
					.setMessage("Error al crear la tienda: el usuario autenticado no existe o carece de los permisos necesarios para crear nuevas tiendas.").build();
		}

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

		if(getUsuarioRepository().existsByIdAndRol(request.getTienda().getIdUserAdmin(), Rol.ADMIN)) {
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
		}else {
			response = ModifyTiendaResponse.newBuilder()
					.setMessage("Error al modificar la tienda: el usuario autenticado no existe o carece de los permisos necesarios para modificar tiendas.")
					.build();
		}

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Transactional(readOnly = true)
	public Tienda findTienda(String codigo) {
		return tiendaRepository.findByCodigo(codigo);
	}

	// ==========================
	// BUSQUEDA
	// ==========================

	@Transactional(readOnly = true)
	public Optional<Set<Tienda>> findTiendas(String codigo, Boolean habilitada, String username) {
		// Solo disponible para usuarios de casa central
		Usuario usuario = usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Acceso denegado: usuario no encontrado."));

		if (usuario.getTienda() == null || !usuario.esDeCasaCentral())
			throw new RuntimeException("Acceso denegado: solo usuarios de casa central pueden realizar esta acción.");

		Set<Tienda> resultado = new HashSet<>();

		if (codigo != null && habilitada != null) {
			resultado.addAll(tiendaRepository.findByCodigoAndHabilitada(codigo, habilitada));
		} else if (codigo != null) {
			Tienda tienda = findTienda(codigo);
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
	public void findTiendas(FindTiendasRequest request, StreamObserver<FindTiendasResponse> responseObserver) {
		// Obtener los parámetros del request
		String codigo = request.getCodigo().isEmpty() ? null : request.getCodigo();
		Boolean habilitada = request.getHabilitada();
		String username = request.getUsername();

		try {
			Optional<Set<Tienda>> tiendasOptional = findTiendas(codigo, habilitada, username);

			// Crear la respuesta gRPC
			FindTiendasResponse.Builder responseBuilder = FindTiendasResponse.newBuilder();

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

	// ==========================
	// GETTERS
	// ==========================

	@Transactional(readOnly = true)
	public Optional<List<Tienda>> getTiendas() {
		List<Tienda> tiendas = tiendaRepository.findAll();
		List<Tienda> tiendasModificadas = new ArrayList<>();

		for (Tienda t : tiendas) {
			Tienda tiendaNueva = new Tienda(t.getId(), t.getCodigo(), t.getDireccion(), t.getCiudad(), t.getProvincia(),
					t.isHabilitada(), t.getEsCasaCentral(), t.getProductos());
			tiendasModificadas.add(tiendaNueva);
		}

		return tiendasModificadas.isEmpty() ? Optional.empty() : Optional.of(tiendasModificadas);
	}

	@Transactional(readOnly = true)
	@Override
	public void getTiendas(GetTiendasRequest request, StreamObserver<GetTiendasResponse> responseObserver) {
		Optional<List<Tienda>> tiendas = getTiendas();
		GetTiendasResponse.Builder responseBuilder = GetTiendasResponse.newBuilder();

		if (tiendas.isPresent()) {
			for (Tienda tienda : tiendas.get()) {
				responseBuilder.addTiendas(convertToProtoTienda(tienda));
			}
		}

		responseObserver.onNext(responseBuilder.build());
		responseObserver.onCompleted();
	}
	
	@Transactional
    public void crearOrdenCompra(Tienda tienda, List<ItemOrden> items) {
        // Crear la orden de compra
        OrdenCompra ordenCompra = new OrdenCompra();
        ordenCompra.setTienda(tienda);
        ordenCompra.setItems(items);
        ordenCompra.setFechaSolicitud(LocalDate.now());
        ordenCompra.setEstado("SOLICITADA");

        // Guardar en la base de datos
        ordenCompraRepository.save(ordenCompra);

        // Construir el mensaje para Kafka
        String mensaje = construirMensajeKafka(tienda.getCodigo(), ordenCompra);

        // Enviar el mensaje a Kafka
        ordenCompraProducer.enviarOrden(mensaje);
    }

    private String construirMensajeKafka(String codigoTienda, OrdenCompra ordenCompra) {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Codigo Tienda: ").append(codigoTienda).append(", ");
        mensaje.append("ID Orden: ").append(ordenCompra.getId()).append(", ");
        mensaje.append("Items: ");
        for (ItemOrden item : ordenCompra.getItems()) {
            mensaje.append("[Código: ").append(item.getCodigoArticulo())
                   .append(", Color: ").append(item.getColor())
                   .append(", Talle: ").append(item.getTalle())
                   .append(", Cantidad: ").append(item.getCantidad()).append("], ");
        }
        mensaje.append("Fecha de Solicitud: ").append(ordenCompra.getFechaSolicitud());
        return mensaje.toString();
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
