package com.unla.soapsys.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import generated.HelloRequest;
import generated.HelloResponse;

@Endpoint
public class HelloWorldEndpoint {

    private static final String NAMESPACE_URI = "http://example.com/helloworld";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "HelloRequest")
    @ResponsePayload
    public HelloResponse sayHello(@RequestPayload HelloRequest request) {
        HelloResponse response = new HelloResponse();
        response.setMessage("Hola, " + request.getName() + "!");
        return response;
    }
}