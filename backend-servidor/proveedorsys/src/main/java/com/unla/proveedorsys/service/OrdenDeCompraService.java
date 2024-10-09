package com.unla.proveedorsys.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.unla.proveedorsys.enums.EstadoOrden;
import com.unla.proveedorsys.model.OrdenDeCompra;
import com.unla.proveedorsys.model.OrdenDeDespacho;
import com.unla.proveedorsys.model.Producto;
import com.unla.proveedorsys.repository.OrdenDeCompraRepository;
import com.unla.proveedorsys.repository.OrdenDeDespachoRepository;
import com.unla.proveedorsys.repository.ProductoRepository;
import com.unla.proveedorsys.repository.StockRepository;
import com.unla.proveedorsys.repository.TiendaRepository;

@Service
public class OrdenDeCompraService {

	@Autowired
	private KafkaProducerService kafkaProducerService;

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private OrdenDeDespachoRepository orderDeDespachoRepository;

	@Autowired
	private OrdenDeCompraRepository ordenDeCompraRepository;

	@Autowired
	private TiendaRepository tiendaRepository;

	@Autowired
	private ProductoRepository productoRepository;

	private static final String TOPIC_ORDENES = "orden-de-compra";
	private static final String TOPIC_SOLICITUDES = "solicitudes";
	private static final String TOPIC_DESPACHO = "despacho";

	/**
	 * Enviar orden de compra al topic correspondiente.
	 *
	 * @param orden La orden de compra que se va a enviar.
	 */
	public void enviarOrdenDeCompra(OrdenDeCompra orden) {
		orden.setFechaSolicitud(LocalDate.now());
		orden.setEstado(EstadoOrden.SOLICITADA);

		// Convertir la orden a JSON
		ObjectMapper mapper = new ObjectMapper();

		try {
			String mensaje = mapper.writeValueAsString(orden);
			kafkaProducerService.sendMessage(TOPIC_ORDENES, String.valueOf(orden.getId()), mensaje);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Actualiza el estado de la orden de compra.
	 *
	 * @param orden  La orden de compra que se va a actualizar.
	 * @param estado El nuevo estado a asignar.
	 */
	public void actualizarEstadoOrden(OrdenDeCompra orden, String estado) {
		orden.setEstado(EstadoOrden.valueOf(estado));
		// Se envía la actualización al mismo topic para reflejar el cambio.
		ObjectMapper mapper = new ObjectMapper();
		try {
			String mensaje = mapper.writeValueAsString(orden);
			kafkaProducerService.sendMessage(TOPIC_ORDENES, String.valueOf(orden.getId()), mensaje);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Transactional(readOnly = false, rollbackForClassName = { "java.lang.Throwable",
			"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	public void procesarOrdenDeCompra(OrdenDeCompra orden) {
		StringBuilder observacionesProcesoOrden = new StringBuilder();

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String mensaje;

		Producto producto = productoRepository.findByCodigo(orden.getCodigoArticulo());
		if (producto == null) {
			observacionesProcesoOrden.append("Artículo ").append(orden.getCodigoArticulo()).append(": no existe. ");
			orden.setObservaciones(observacionesProcesoOrden.toString());
			orden.setEstado(EstadoOrden.RECHAZADA);
			tiendaRepository.save(orden.getTienda());
			ordenDeCompraRepository.save(orden);
			try {
				mensaje = mapper.writeValueAsString(orden);
				kafkaProducerService.sendMessage(TOPIC_SOLICITUDES, String.valueOf(orden.getId()), mensaje);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		} else if (orden.getCantidadSolicitada() < 1) {
			observacionesProcesoOrden.append("Artículo ").append(orden.getCodigoArticulo())
					.append(": cantidad mal informada.");
			orden.setObservaciones(observacionesProcesoOrden.toString());
			orden.setEstado(EstadoOrden.RECHAZADA);
			tiendaRepository.save(orden.getTienda());
			ordenDeCompraRepository.save(orden);
			try {
				mensaje = mapper.writeValueAsString(orden);
				kafkaProducerService.sendMessage(TOPIC_SOLICITUDES, String.valueOf(orden.getId()), mensaje);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		} else if (producto.getCantidad() < orden.getCantidadSolicitada()) {
			observacionesProcesoOrden.append("Articulo ").append(orden.getCodigoArticulo())
					.append(": cantidad de stock insuficiente");
			orden.setEstado(EstadoOrden.ACEPTADA);
			tiendaRepository.save(orden.getTienda());
			orden.setObservaciones(observacionesProcesoOrden.toString());
			ordenDeCompraRepository.save(orden);
			try {
				mensaje = mapper.writeValueAsString(orden);
				kafkaProducerService.sendMessage(TOPIC_SOLICITUDES, String.valueOf(orden.getId()), mensaje);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		} else {
			orden.setEstado(EstadoOrden.ACEPTADA);
			try {
				mensaje = mapper.writeValueAsString(orden);
				orden.setObservaciones("Se genero con exito el pedido de compra");
				kafkaProducerService.sendMessage(TOPIC_SOLICITUDES, String.valueOf(orden.getId()), mensaje);

				tiendaRepository.save(orden.getTienda());
				orden = ordenDeCompraRepository.save(orden);

				OrdenDeDespacho ordenDeDespacho = new OrdenDeDespacho();
				ordenDeDespacho.setFechaDeEnvio(LocalDateTime.now().plusDays(1));
				ordenDeDespacho.setOrdenDeCompra(orden);
				ordenDeDespacho = orderDeDespachoRepository.save(ordenDeDespacho);

				orden.setId_orden_despacho(ordenDeDespacho.getId());
				orden = ordenDeCompraRepository.save(orden);

				producto.setCantidad(producto.getCantidad() - orden.getCantidadSolicitada());
				productoRepository.save(producto);

				mensaje = mapper.writeValueAsString(orden);
				kafkaProducerService.sendMessage(TOPIC_DESPACHO, String.valueOf(orden.getId()), mensaje);

			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}

	@Transactional(readOnly = false, rollbackForClassName = { "java.lang.Throwable",
	"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	public void reprocesoDeOrdenes(Producto producto) {
		List<OrdenDeCompra> listOrdenDeCompra = ordenDeCompraRepository
				.findOrdenesAceptadasSinDespachoPorCodigoArticulo(EstadoOrden.ACEPTADA, producto.getCodigo());
		
		for(OrdenDeCompra orden: listOrdenDeCompra) {
			if (producto.getCantidad() >= orden.getCantidadSolicitada()) {
				procesarOrdenDeCompra(orden);
			}
		}
	}
	
	public List<OrdenDeCompra> getList() {
		List<OrdenDeCompra> list = ordenDeCompraRepository.findAll();
    	return list;
	}
}