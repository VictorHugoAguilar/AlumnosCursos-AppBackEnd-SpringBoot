package com.microservicios.app.microservicios.examenes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservicios.app.common.examenes.models.entity.Asignatura;
import com.microservicios.app.common.examenes.models.entity.Examen;
import com.microservicios.app.commons.services.CommonServiceImpl;
import com.microservicios.app.microservicios.examenes.models.repository.AsignaturaRepository;
import com.microservicios.app.microservicios.examenes.models.repository.ExamenRepository;

@Service
public class ExamenServiceImpl extends CommonServiceImpl<Examen, ExamenRepository> implements ExamenService {

	@Autowired
	private AsignaturaRepository asignaturaRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Examen> findByNombre(String term) {
		return repository.findByNombre(term);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Asignatura> findAllAsignaturas() {
		return asignaturaRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Long> findExamenesIdsConRespuestasByPreguntaIds(Iterable<Long> preguntasIds) {
		return repository.findExamenesIdsConRespuestasByPreguntaIds(preguntasIds);
	}

}
