package com.microservicios.app.microservicios.cursos.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.microservicios.app.common.alumnos.models.entity.Alumno;

@FeignClient(name = "microservicio-usuarios")
public interface AlumnoFeignClient {

	@GetMapping("/alumnos-por-curso")
	public Iterable<Alumno> obtenerAlumnosPorCurso(@RequestParam List<Long> ids);

}
