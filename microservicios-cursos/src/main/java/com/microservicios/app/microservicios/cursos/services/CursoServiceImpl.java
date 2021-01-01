package com.microservicios.app.microservicios.cursos.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservicios.app.commons.services.CommonServiceImpl;
import com.microservicios.app.microservicios.cursos.models.entity.Curso;
import com.microservicios.app.microservicios.cursos.models.repository.CursoRepository;

@Service
public class CursoServiceImpl extends CommonServiceImpl<Curso, CursoRepository> implements CursoService {

	@Override
	@Transactional(readOnly = true)
	public Curso findCursoByAlumnoId(Long id) {
		return repository.findCursoByAlumnoId(id);
	}

}
