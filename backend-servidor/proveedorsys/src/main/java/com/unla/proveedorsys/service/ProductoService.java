package com.unla.proveedorsys.service;

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

    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> getProductoById(Long id) {
        return productoRepository.findById(id);
    }

    public Producto saveOrUpdateProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }

    public Optional<Producto> getProductoByCodigo(String codigo) {
        return Optional.ofNullable(productoRepository.findByCodigo(codigo));
    }
}
