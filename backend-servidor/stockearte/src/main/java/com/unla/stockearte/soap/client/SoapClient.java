package com.unla.stockearte.soap.client;

import javax.xml.namespace.QName;
import jakarta.xml.ws.Service;
import java.net.URL;
import java.net.URI;
import com.example.soapsys.controller.SoapEndpoint;

public class SoapClient {
    public static void main(String[] args) throws Exception {
        
        URI uri = new URI("http://localhost:8081/soap?wsdl");
        URL url = uri.toURL();  //

        QName qname = new QName("http://soapsys.example.com/", "SoapEndpointService");
        Service service = Service.create(url, qname);

        SoapEndpoint soap = service.getPort(SoapEndpoint.class);
        String response = soap.receiveMessage("Hola Mundo desde Stockearte!");
        System.out.println(response);
    }
}
