package com.microservicios.app.microservicios.cursos.services;

import org.springframework.web.bind.annotation.PathVariable;

import com.microservicios.app.commons.services.CommonService;
import com.microservicios.app.microservicios.cursos.models.entity.Curso;

public interface CursoService extends CommonService<Curso> {

	public Curso findCursoByAlumnoId(Long id);
	
	public Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(@PathVariable Long alumnoId);

}
