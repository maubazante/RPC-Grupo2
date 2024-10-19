package com.unla.soapsys.service;

import com.unla.soapsys.model.Producto;
import com.unla.soapsys.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public String agregarProducto(Producto producto) {
        productoRepository.save(producto);
        return "Producto agregado exitosamente";
    }

    public List<Producto> obtenerProductos() {
        return productoRepository.findAll();
    }
}
