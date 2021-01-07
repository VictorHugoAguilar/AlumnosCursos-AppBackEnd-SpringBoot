package com.microservicios.app.usuarios.models.services;

import java.util.List;

import com.microservicios.app.common.alumnos.models.entity.Alumno;
import com.microservicios.app.commons.services.CommonService;

public interface AlumnoService extends CommonService<Alumno> {

	public List<Alumno> findByNombreOrApellido(String term);

	public Iterable<Alumno> findAllByIds(Iterable<Long> ids);

	public void eliminarAlumnoCursoPorId(Long id);

}
