package com.unla.stockearte.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unla.stockearte.dto.OrdenCompraRequest;
import com.unla.stockearte.enums.EstadoOrden;
import com.unla.stockearte.model.OrdenDeCompra;
import com.unla.stockearte.repository.OrdenDeCompraRepository;

@Service
public class OrdenDeCompraService {

    @Autowired
    private OrdenDeCompraRepository ordenDeCompraRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC_ORDENES = "/orden-de-compra";

    public OrdenDeCompra crearOrdenCompra(OrdenCompraRequest ordenDeCompraReq) {
    	
    	OrdenDeCompra ordenDeCompra = new OrdenDeCompra();
    	
        ordenDeCompra.setEstado(EstadoOrden.SOLICITADA);
        ordenDeCompra.setFechaSolicitud(LocalDateTime.now());
        ordenDeCompra.setCantidadSolicitada(ordenDeCompraReq.getCantidadSolicitada());
        ordenDeCompra.setCodigoArticulo(ordenDeCompraReq.getCodigoArticulo());
        ordenDeCompra.setColor(ordenDeCompraReq.getColor());
        ordenDeCompra.setTalle(ordenDeCompraReq.getTalle());
        
        // Guardar la orden en la base de datos
        OrdenDeCompra nuevaOrden = ordenDeCompraRepository.save(ordenDeCompra);

       //Lo parsea a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String mensaje = objectMapper.writeValueAsString(nuevaOrden);
            kafkaTemplate.send(TOPIC_ORDENES, mensaje);
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
