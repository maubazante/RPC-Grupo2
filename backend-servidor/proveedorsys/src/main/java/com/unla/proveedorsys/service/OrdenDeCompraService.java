package com.unla.proveedorsys.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unla.proveedorsys.enums.EstadoOrden;
import com.unla.proveedorsys.model.ItemOrden;
import com.unla.proveedorsys.model.OrdenDeCompra;
import com.unla.proveedorsys.model.OrdenDeDespacho;
import com.unla.proveedorsys.repository.ItemOrdenRepository;
import com.unla.proveedorsys.repository.OrdenDeCompraRepository;
import com.unla.proveedorsys.repository.OrdenDeDespachoRepository;
import com.unla.proveedorsys.repository.StockRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class OrdenDeCompraService {

	@Autowired
	private KafkaProducerService kafkaProducerService;

	@Autowired
	private ItemOrdenRepository itemOrdenRepository;

	@Autowired
	private StockRepository stockRepository;
	
	@Autowired
	private OrdenDeDespachoRepository orderDeDespachoRepository;
	
	@Autowired
	private OrdenDeCompraRepository ordenDeCompraRepository;

	private static final String TOPIC_ORDENES = "/orden-de-compra";
	private static final String TOPIC_SOLICITUDES = "/solicitudes";
	private static final String TOPIC_DESPACHO = "/despacho";

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

	public void procesarOrdenDeCompra(OrdenDeCompra orden) {
		Boolean hayErrores = Boolean.FALSE;
		Boolean faltaStock = Boolean.FALSE;
		StringBuilder observacionesErrores = new StringBuilder();
		StringBuilder observacionesFaltaStock = new StringBuilder();

		ObjectMapper mapper = new ObjectMapper();
		String mensaje;
		
			if (ordenDeCompraRepository.findByCodigoArticulo(orden.getCodigoArticulo()) == null) {
				hayErrores = Boolean.TRUE;
				observacionesErrores.append("Artículo ").append(orden.getCodigoArticulo()).append(": no existe. ");
			}

			if (orden.getCantidadSolicitada() < 1) {
				hayErrores = Boolean.TRUE;
				observacionesErrores.append("Artículo ").append(orden.getCodigoArticulo())
						.append(": cantidad mal informada.");
			}

			if (!stockRepository.tieneStockSuficiente(orden.getCodigoArticulo(), orden.getCantidadSolicitada())) {
				faltaStock = Boolean.TRUE;
				observacionesFaltaStock.append("Articulo ").append(orden.getCodigoArticulo())
						.append(": cantidad de stock insuficiente");
			}
		

		if (hayErrores) {
			orden.setEstado(EstadoOrden.RECHAZADA);
			ordenDeCompraRepository.save(orden);
			orden.setObservaciones(observacionesErrores.toString());
			try {
				mensaje = mapper.writeValueAsString(orden);
				kafkaProducerService.sendMessage(TOPIC_SOLICITUDES, String.valueOf(orden.getId()), mensaje);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		} else if (faltaStock) {
			orden.setEstado(EstadoOrden.ACEPTADA);
			ordenDeCompraRepository.save(orden);
			orden.setObservaciones(observacionesFaltaStock.toString());
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
				kafkaProducerService.sendMessage(TOPIC_SOLICITUDES, String.valueOf(orden.getId()), mensaje);
				
				
				OrdenDeDespacho ordenDeDespacho = new OrdenDeDespacho();
				ordenDeDespacho.setFechaDeEnvio(LocalDateTime.now().plusDays(1));
				ordenDeDespacho.setOrdenDeCompra(orden);
				ordenDeDespacho = orderDeDespachoRepository.save(ordenDeDespacho);
				
				orden.setId_orden_despacho(ordenDeDespacho.getId());
				ordenDeCompraRepository.save(orden);
				
				//TODO: Agarrar el stock por cada producto y setear la cantidad de lo que se pidio
				
				kafkaProducerService.sendMessage(TOPIC_DESPACHO, String.valueOf(orden.getId()), mensaje);
				
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			
		}
		
	}
}