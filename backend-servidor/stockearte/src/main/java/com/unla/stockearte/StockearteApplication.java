package com.unla.stockearte;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockearteApplication {
	
	
	private final GrpcServer grpcServer;
	
	public StockearteApplication(GrpcServer grpcServer) {
        this.grpcServer = grpcServer;
    }

	public static void main(String[] args) {
		SpringApplication.run(StockearteApplication.class, args);
	}
	
	@PostConstruct
    public void startGrpcServer() throws IOException, InterruptedException {
        grpcServer.start();
        //grpcServer.blockUntilShutdown();
    }

}
