package com.microservicios.app.microservicios.cursos.services;

import com.microservicios.app.commons.services.CommonService;
import com.microservicios.app.microservicios.cursos.models.entity.Curso;

public interface CursoService extends CommonService<Curso> {

	public Curso findCursoByAlumnoId(Long id);
}
