package com.example.soapsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})

public class SoapsysApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoapsysApplication.class, args);
	}

}
