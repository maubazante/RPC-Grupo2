package com.unla.stockearte;

import com.unla.stockearte.service.GreeterImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {

    private Server server;

    public void start() throws IOException {
        // Iniciar el servidor en el puerto 9090
        server = ServerBuilder.forPort(9090)
                .addService(new GreeterImpl()) // Registrar el servicio Greeter
                .build()
                .start();
        System.out.println("Servidor gRPC iniciado en el puerto 9090...");

        // Registrar un hook para apagar el servidor cuando se cierre la aplicacion
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Apagando servidor gRPC...");
            GrpcServer.this.stop();
            System.err.println("Servidor gRPC apagado.");
        }));
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    // Esperar a que el servidor se apague
    private void blockUntilShutdown() throws InterruptedException {
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
