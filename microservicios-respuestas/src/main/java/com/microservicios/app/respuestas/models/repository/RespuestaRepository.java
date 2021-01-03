package com.microservicios.app.respuestas.models.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.microservicios.app.respuestas.models.entity.Respuesta;

public interface RespuestaRepository extends MongoRepository<Respuesta, String> {

	@Query("{'alumnoId' : ?0,  'preguntaId' : { $in : ?1}}")
	public Iterable<Respuesta> findRespuestaByAlumnoByPreguntaIds(Long alumnoId, Iterable<Long> preguntaId);

	@Query("{'alumnoId' : ?0 }")
	public Iterable<Respuesta> findByAlumnoId(Long alumnoId);
	
	@Query("{ 'alumnoId' : ?0, 'pregunta.examen.id' : ?1 }")
	public Iterable<Respuesta>findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId); 
	
	@Query(value = "{'alumnoId' : ?0 }", fields = "{ 'pregunta.examen.id' : 1 }")
	public Iterable<Respuesta> findExamenesIdsConRespuestasByAlumno(Long alumnoId);

}
