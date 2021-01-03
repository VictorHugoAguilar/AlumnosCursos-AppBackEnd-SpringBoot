package com.microservicios.app.respuestas.services;

import org.springframework.web.bind.annotation.PathVariable;

import com.microservicios.app.common.examenes.models.entity.Examen;
import com.microservicios.app.respuestas.models.entity.Respuesta;

public interface RespuestaService {

	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas);

	public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId);

	public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId);
	
	public Examen obtenerExamenPorId(@PathVariable Long id);
	
	public Iterable<Respuesta> findByAlumnoId(Long alumnoId);

}
