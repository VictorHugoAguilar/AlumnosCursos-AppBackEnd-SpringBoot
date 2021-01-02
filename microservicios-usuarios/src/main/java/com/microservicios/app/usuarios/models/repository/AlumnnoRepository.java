package com.microservicios.app.usuarios.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.microservicios.app.common.alumnos.models.entity.Alumno;

public interface AlumnnoRepository extends PagingAndSortingRepository<Alumno, Long> {

	@Query("select a from Alumno a where upper(a.nombre) like upper(concat('%', ?1, '%')) or upper(a.apellido) like upper(concat('%', ?1, '%'))")
	public List<Alumno> findByNombreOrApellido(String term);

}
