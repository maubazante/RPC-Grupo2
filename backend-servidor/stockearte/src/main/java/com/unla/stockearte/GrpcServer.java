package com.unla.stockearte;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.unla.stockearte.service.GreeterImpl;
import com.unla.stockearte.service.ProductoService;
import com.unla.stockearte.service.TiendaService;
import com.unla.stockearte.service.UsuarioService;

import io.grpc.Server;
import io.grpc.ServerBuilder;

@Component
public class GrpcServer {
	
    private Server server;
    
    @Autowired()
    private TiendaService tiendaService;
    
    @Autowired()
    private UsuarioService usuarioService;
    
    @Autowired()
    private ProductoService productoService;
    
    private static final Logger logger = LoggerFactory.getLogger(GrpcServer.class);

    public void start() throws IOException {
        // Iniciar el servidor en el puerto 9090
        server = ServerBuilder.forPort(9090)
                .addService(new GreeterImpl()) // Registrar el servicio Greeter
                .addService(tiendaService)
                .addService(usuarioService)
                .addService(productoService)
                .build()
                .start();
        logger.info("Servidor gRPC iniciado en el puerto 9090...");

        // Registrar un hook para apagar el servidor cuando se cierre la aplicacion
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        	logger.info("Apagando servidor gRPC...");
            GrpcServer.this.stop();
            logger.info("Servidor gRPC apagado.");
        }));
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    // Esperar a que el servidor se apague
    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        GrpcServer server = new GrpcServer();
        server.start();
        server.blockUntilShutdown();
    }
}
