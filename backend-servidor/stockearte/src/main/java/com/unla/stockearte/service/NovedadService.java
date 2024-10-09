package com.unla.stockearte.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.stockearte.dto.AltaProductoRequest;
import com.unla.stockearte.model.Novedad;
import com.unla.stockearte.model.Producto;
import com.unla.stockearte.model.Stock;
import com.unla.stockearte.model.StockId;
import com.unla.stockearte.model.Tienda;
import com.unla.stockearte.repository.NovedadRepository;
import com.unla.stockearte.repository.ProductoRepository;
import com.unla.stockearte.repository.StockRepository;
import com.unla.stockearte.repository.TiendaRepository;

@Service
public class NovedadService {

    @Autowired
    private NovedadRepository novedadRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

    public Novedad saveNovedad(Novedad novedad) {
        return novedadRepository.save(novedad);
    }
    
    public List<Novedad> getAllNovedades() {
        return novedadRepository.findAll();
    }
    
    public boolean darDeAltaProducto(AltaProductoRequest request) {
        Optional<Novedad> novedadOptional = novedadRepository.findById((long) request.getId());
        
        if (!novedadOptional.isPresent()) {
            throw new RuntimeException("Novedad no encontrada con el ID: " + request.getId());
        }

        Novedad novedad = novedadOptional.get();

        if (request.getCantidad() >= novedad.getCantidad()) {
            novedadRepository.delete(novedad);
        } else {
            int nuevaCantidad = novedad.getCantidad() - request.getCantidad();
            novedad.setCantidad(nuevaCantidad);
            novedadRepository.save(novedad); 
        }
        
        Producto producto = new Producto();
        Optional<Producto> productoOptional = productoRepository.findByCodigo(request.getCodigo());
        
        if(productoOptional.isPresent()) {
        	producto = productoOptional.get();
        	producto.setCantidad(producto.getCantidad() + request.getCantidad());
        } else {
        	producto.setNombre(request.getNombre());
            producto.setCodigo(request.getCodigo());
            producto.setTalle(request.getTalle());
            producto.setColor(request.getColor());
            producto.setFoto(request.getFoto());
            producto.setCantidad(request.getCantidad());
            producto.setHabilitado(request.isHabilitado());
        }
       
        Producto productoPersistido = productoRepository.save(producto);
        
        if(productoOptional.isEmpty()) {
        List<Stock> stockList = request.getTiendaIds().stream()
            .map(tiendaId -> {
                Tienda tienda = tiendaRepository.findById(tiendaId)
                        .orElseThrow(() -> new RuntimeException("Tienda no encontrada con ID: " + tiendaId));
                
                Stock stock = new Stock();
                Optional<Stock> stockOpt = stockRepository.findById(new StockId(productoPersistido.getId(), tienda.getId()));
                
                if(stockOpt.isPresent()) {
                	stock = stockOpt.get();
                	stock.setStock(stock.getStock() + request.getCantidad());
                } else {
                	stock.setId(new StockId(productoPersistido.getId(), tienda.getId()));
                    stock.setProducto(productoPersistido);
                    stock.setTienda(tienda);
                    stock.setStock(request.getCantidad());
                }
                
                return stock;
            })
            .collect(Collectors.toList());

        stockRepository.saveAll(stockList);
        }
        
        return true;
    }
}