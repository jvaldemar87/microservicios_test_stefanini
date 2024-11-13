package com.ejemplo.microservicios_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroserviciosTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosTestApplication.class, args);
		System.out.println("hola mundo");
	}

}
