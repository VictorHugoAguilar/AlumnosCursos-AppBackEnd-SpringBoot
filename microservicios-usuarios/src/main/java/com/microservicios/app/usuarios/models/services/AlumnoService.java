package com.microservicios.app.usuarios.models.services;

import java.util.List;

import com.microservicios.app.commons.services.CommonService;
import com.microservicios.app.microservicios.commons.alumnos.models.entity.Alumno;

public interface AlumnoService extends CommonService<Alumno>  {

	public List<Alumno> findByNombreOrApellido(String term);

}
