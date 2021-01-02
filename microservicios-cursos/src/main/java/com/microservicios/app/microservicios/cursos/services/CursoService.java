package com.microservicios.app.microservicios.cursos.services;

import java.util.List;

import com.microservicios.app.common.alumnos.models.entity.Alumno;
import com.microservicios.app.commons.services.CommonService;
import com.microservicios.app.microservicios.cursos.models.entity.Curso;

public interface CursoService extends CommonService<Curso> {

	public Curso findCursoByAlumnoId(Long id);
	
	public Iterable<Long> obtenerExamenesIdsConRespuestasAlumno( Long alumnoId);

	public Iterable<Alumno> obtenerAlumnosPorCurso( List<Long> ids);

	public void eliminarCursoAlumnoPorId(Long id);

}
