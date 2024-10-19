package com.unla.soapsys.endpoint;

import com.unla.soapsys.model.Producto;
import com.unla.soapsys.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import com.unla.soapsys.ws.agregarProductoRequest;
import com.unla.soapsys.ws.agregarProductoResponse;
import com.unla.soapsys.ws.obtenerProductosRequest;
import com.unla.soapsys.ws.obtenerProductosResponse;

import java.util.List;

@Endpoint
public class ProductoEndpoint {

    private static final String NAMESPACE_URI = "http://unla.com/soapsys";

    @Autowired
    private ProductoService productoService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "agregarProductoRequest")
    @ResponsePayload
    public agregarProductoResponse agregarProducto(@RequestPayload agregarProductoRequest request) {
        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setCodigo(request.getCodigo());
        producto.setTalle(request.getTalle());
        producto.setFoto(request.getFoto());
        producto.setColor(request.getColor());
        producto.setCantidad(request.getCantidad());

        String resultado = productoService.agregarProducto(producto);

        agregarProductoResponse response = new agregarProductoResponse();
        response.setResultado(resultado);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "obtenerProductosRequest")
    @ResponsePayload
    public obtenerProductosResponse obtenerProductos() {
        List<Producto> productos = productoService.obtenerProductos();
        obtenerProductosResponse response = new obtenerProductosResponse();

        for (Producto producto : productos) {
            response.getProductos().add(producto.getNombre());
        }

        return response;
    }
}
