package com.microservicios.app.microservicios.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({ "com.microservicios.app.common.alumnos.models.entity",
		"com.microservicios.app.microservicios.cursos.models.entity",
		"com.microservicios.app.common.examenes.models.entity" })
public class MicroserviciosCursosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosCursosApplication.class, args);
	}

}
