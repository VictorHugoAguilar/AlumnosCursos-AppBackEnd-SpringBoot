package com.microservicios.app.usuarios.models.services;

import java.util.List;

 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservicios.app.common.alumnos.models.entity.Alumno;
import com.microservicios.app.commons.services.CommonServiceImpl;
import com.microservicios.app.usuarios.models.repository.AlumnnoRepository;

@Service
public class AlumnoServiceImpl extends CommonServiceImpl<Alumno, AlumnnoRepository> implements AlumnoService {

	@Override
	@Transactional(readOnly = true)
	public List<Alumno> findByNombreOrApellido(String term) {
		return repository.findByNombreOrApellido(term);
	}

}