package com.microservicios.app.microservicios.examenes.models.repository;

import org.springframework.data.repository.CrudRepository;

import com.microservicios.app.common.examenes.models.entity.Asignatura;

public interface AsignaturaRepository extends CrudRepository<Asignatura, Long> {

}
