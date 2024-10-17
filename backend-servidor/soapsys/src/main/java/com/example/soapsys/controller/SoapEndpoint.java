package com.example.soapsys.controller;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService
public class SoapEndpoint {
    @WebMethod
    public String receiveMessage(String message) {
        System.out.println("Mensaje recibido: " + message);
        return "Mensaje procesado: " + message;
    }
}
