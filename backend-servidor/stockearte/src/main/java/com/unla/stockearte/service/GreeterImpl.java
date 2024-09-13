package com.unla.stockearte.service;

import io.grpc.stub.StreamObserver;

// Implementacion del servicio Greeter
public class GreeterImpl extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        // Crear la respuesta
        HelloReply reply = HelloReply.newBuilder()
                .setMessage("Mauba, si hiciste bien las cosas acá te tira NodeJS -> " + request.getName())
                .build();

        // Enviar la respuesta al cliente
        responseObserver.onNext(reply);
        // Completar la llamada
        responseObserver.onCompleted();
    }
}
