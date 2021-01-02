package com.microservicios.app.microservicios.cursos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservicios.app.common.alumnos.models.entity.Alumno;
import com.microservicios.app.commons.services.CommonServiceImpl;
import com.microservicios.app.microservicios.cursos.clients.AlumnoFeignClient;
import com.microservicios.app.microservicios.cursos.clients.RespuestaFeignClient;
import com.microservicios.app.microservicios.cursos.models.entity.Curso;
import com.microservicios.app.microservicios.cursos.models.repository.CursoRepository;

@Service
public class CursoServiceImpl extends CommonServiceImpl<Curso, CursoRepository> implements CursoService {

	@Autowired
	private RespuestaFeignClient client;

	@Autowired
	private AlumnoFeignClient clientAlumno;

	@Override
	@Transactional(readOnly = true)
	public Curso findCursoByAlumnoId(Long id) {
		return repository.findCursoByAlumnoId(id);
	}

	@Override
	public Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(Long alumnoId) {
		return client.obtenerExamenesIdsConRespuestasAlumno(alumnoId);
	}

	@Override
	public Iterable<Alumno> obtenerAlumnosPorCurso(List<Long> ids) {
		return clientAlumno.obtenerAlumnosPorCurso(ids);
	}

	@Override
	@Transactional
	public void eliminarCursoAlumnoPorId(Long id) {
		repository.eliminarCursoAlumnoPorId(id);
	}

}
