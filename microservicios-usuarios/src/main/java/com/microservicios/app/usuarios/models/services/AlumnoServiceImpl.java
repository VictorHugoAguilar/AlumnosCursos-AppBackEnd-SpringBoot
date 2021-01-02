package com.microservicios.app.usuarios.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservicios.app.common.alumnos.models.entity.Alumno;
import com.microservicios.app.commons.services.CommonServiceImpl;
import com.microservicios.app.usuarios.client.CursoFeignClient;
import com.microservicios.app.usuarios.models.repository.AlumnnoRepository;

@Service
public class AlumnoServiceImpl extends CommonServiceImpl<Alumno, AlumnnoRepository> implements AlumnoService {

	@Autowired
	private CursoFeignClient clienteCurso;

	@Override
	@Transactional(readOnly = true)
	public List<Alumno> findByNombreOrApellido(String term) {
		return repository.findByNombreOrApellido(term);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumno> findAllByIds(Iterable<Long> ids) {
		return repository.findAllById(ids);
	}

	@Override
	public void eliminarAlumnoCursoPorId(Long id) {
		clienteCurso.eliminarAlumnoCursoPorId(id);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		super.deleteById(id);
		this.eliminarAlumnoCursoPorId(id);
	}

}