package com.unla.stockearte.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.unla.stockearte.dto.OrdenCompraRequest;
import com.unla.stockearte.enums.EstadoOrden;
import com.unla.stockearte.model.OrdenDeCompra;
import com.unla.stockearte.model.Producto;
import com.unla.stockearte.model.Stock;
import com.unla.stockearte.model.StockId;
import com.unla.stockearte.model.Tienda;
import com.unla.stockearte.repository.OrdenDeCompraRepository;
import com.unla.stockearte.repository.ProductoRepository;
import com.unla.stockearte.repository.StockRepository;
import com.unla.stockearte.repository.TiendaRepository;

@Service
public class OrdenDeCompraService {

	@Autowired
	private OrdenDeCompraRepository ordenDeCompraRepository;

	@Autowired
	private TiendaRepository tiendaRepository;

	@Autowired
	private ProductoRepository productoRepository;

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	KafkaProducerService kafkaService;

	private static final String TOPIC_ORDENES = "orden-de-compra";
	private static final String TOPIC_RECEPCION = "recepcion";
	private static final String TOPIC_PROVEEDOR_SYS = "proveedor-sys";

	@Transactional(readOnly = false, rollbackForClassName = { "java.lang.Throwable",
			"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	public OrdenDeCompra crearOrdenCompra(OrdenCompraRequest ordenDeCompraReq) {

		OrdenDeCompra ordenDeCompra = new OrdenDeCompra();

		ordenDeCompra.setEstado(EstadoOrden.SOLICITADA);
		ordenDeCompra.setFechaSolicitud(LocalDate.now());
		ordenDeCompra.setCantidadSolicitada(ordenDeCompraReq.getCantidadSolicitada());
		ordenDeCompra.setCodigoArticulo(ordenDeCompraReq.getCodigoArticulo());
		ordenDeCompra.setColor(ordenDeCompraReq.getColor());
		ordenDeCompra.setTalle(ordenDeCompraReq.getTalle());
		ordenDeCompra
				.setTienda((Tienda) Hibernate.unproxy(tiendaRepository.getReferenceById(ordenDeCompraReq.getTienda())));

		// Guardar la orden en la base de datos
		OrdenDeCompra nuevaOrden = ordenDeCompraRepository.save(ordenDeCompra);

		// Lo parsea a JSON
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		try {
			String mensaje = objectMapper.writeValueAsString(nuevaOrden);
			kafkaService.sendMessage(TOPIC_ORDENES, mensaje);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return nuevaOrden;
	}

	@Transactional(readOnly = false, rollbackForClassName = { "java.lang.Throwable",
			"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	public OrdenDeCompra marcarOrdenComoRecibida(Long idOrden) {
		OrdenDeCompra orden = ordenDeCompraRepository.getReferenceById(idOrden);

		// Verifica que la orden esté en estado ACEPTADA y tenga id de orden de despacho
		if (orden.getEstado() != EstadoOrden.ACEPTADA || orden.getId_orden_despacho() == null) {
			throw new IllegalArgumentException("La orden no puede ser marcada como recibida");
		}

		// Actualiza la fecha de recepción y el estado de la orden
		orden.setFechaRecepcion(LocalDate.now());
		orden.setEstado(EstadoOrden.RECIBIDA);

		// Actualiza el stock
		Optional<Producto> producto = productoRepository.findByCodigo(orden.getCodigoArticulo());
		if (producto.get() != null) {
			// Obtener el stock del producto en la tienda correspondiente
			Stock stock = stockRepository.findByProductoCodigoAndTiendaId(orden.getCodigoArticulo(),
					orden.getTienda().getId());

			if (stock != null) {
				// Actualizar el stock existente
				stock.setStock(stock.getStock() + orden.getCantidadSolicitada());
				stockRepository.save(stock); // Guardar el stock actualizado
			} else {
				// Crear un nuevo stock si no se encuentra
				Stock nuevoStock = new Stock();
				StockId nuevoStockId = new StockId();
				nuevoStockId.setTiendaId(orden.getTienda().getId());
				nuevoStockId.setProductoId(producto.get().getId());
				nuevoStock.setId(nuevoStockId);
				nuevoStock.setProducto(producto.get());
				nuevoStock.setTienda(orden.getTienda());
				nuevoStock.setStock(orden.getCantidadSolicitada());
				stockRepository.save(nuevoStock); // Guardar el nuevo stock
			}

			// Actualizar la cantidad del producto
			producto.get().setCantidad(producto.get().getCantidad() + orden.getCantidadSolicitada());
			productoRepository.save(producto.get());
		} else {
			// Manejar el caso en que no se encuentra el producto
			throw new IllegalArgumentException("No se encontró el producto con código: " + orden.getCodigoArticulo());
		}

		// Guarda la orden actualizada
		OrdenDeCompra ordenActualizada = ordenDeCompraRepository.save(orden);

		// Envía un mensaje a Kafka para el topic '/recepcion'
		enviarMensajeKafka(TOPIC_RECEPCION, ordenActualizada);

		// Envía un mensaje a el topic de ProveedorSys
		enviarMensajeKafka(TOPIC_PROVEEDOR_SYS, ordenActualizada);

		return ordenActualizada;
	}

	private void enviarMensajeKafka(String topic, OrdenDeCompra ordenActualizada) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			String mensaje = objectMapper.writeValueAsString(ordenActualizada);
			kafkaService.sendMessage(topic, mensaje);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public List<OrdenDeCompra> getList() {
		List<OrdenDeCompra> list = ordenDeCompraRepository.findAll();
		return list;
	}

	public OrdenDeCompra getById(Long id) {
		OrdenDeCompra order = ordenDeCompraRepository.getReferenceById(id);
		return order;
	}

	public Boolean deleteById(Long id) {
		ordenDeCompraRepository.deleteById(id);
		return Boolean.TRUE;
	}
}
