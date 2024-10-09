package com.unla.stockearte.service;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.unla.stockearte.dto.OrdenCompraRequest;
import com.unla.stockearte.enums.EstadoOrden;
import com.unla.stockearte.model.OrdenDeCompra;
import com.unla.stockearte.model.Producto;
import com.unla.stockearte.model.Tienda;
import com.unla.stockearte.repository.OrdenDeCompraRepository;
import com.unla.stockearte.repository.ProductoRepository;
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
    KafkaProducerService kafkaService;
    
    private static final String TOPIC_ORDENES = "orden-de-compra";
    
    
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
        ordenDeCompra.setTienda((Tienda) Hibernate.unproxy(tiendaRepository.getReferenceById(ordenDeCompraReq.getTienda())));
        
        
        // Guardar la orden en la base de datos
        OrdenDeCompra nuevaOrden = ordenDeCompraRepository.save(ordenDeCompra);

       //Lo parsea a JSON
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
