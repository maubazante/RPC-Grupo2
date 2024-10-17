package com.example.soapsys.service;

import javax.xml.ws.Endpoint;

public class SoapService {
    public static void main(String[] args) {
        com.example.soapsys.controller.SoapEndpoint implementor = new com.example.soapsys.controller.SoapEndpoint();
        String address = "http://localhost:8081/soap";
        Endpoint.publish(address, implementor);
    }
}
