package com.unla.proveedorsys.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unla.proveedorsys.model.Producto;
import com.unla.proveedorsys.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;  

    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public Producto createProducto(Producto producto) {
        Producto nuevoProducto = productoRepository.save(producto);
        enviarNovedadesKafka(nuevoProducto);
        return nuevoProducto;
    }

    public Producto updateProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }

    // Método para enviar el mensaje al tópico Kafka /novedades usando KafkaProducerService
    private void enviarNovedadesKafka(Producto producto) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String mensajeJson = mapper.writeValueAsString(producto);  // Convertir el producto a JSON
            kafkaProducerService.sendMessage("novedades", producto.getCodigo(), mensajeJson);  // Usar KafkaProducerService
            System.out.println("Mensaje enviado a Kafka: " + mensajeJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

	public Optional<Producto> getProductoById(Long id) {
		return productoRepository.findById(id);
	}

	public Optional<Producto> getProductoByCodigo(String codigo) {
		return Optional.ofNullable(productoRepository.findByCodigo(codigo));
	}
}
