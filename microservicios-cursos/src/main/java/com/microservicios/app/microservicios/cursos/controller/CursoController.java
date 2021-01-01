package com.microservicios.app.microservicios.cursos.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.app.common.alumnos.models.entity.Alumno;
import com.microservicios.app.common.examenes.models.entity.Examen;
import com.microservicios.app.commons.controllers.CommonController;
import com.microservicios.app.microservicios.cursos.models.entity.Curso;
import com.microservicios.app.microservicios.cursos.services.CursoService;

@RestController
public class CursoController extends CommonController<Curso, CursoService> {

	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {

		if (result.hasErrors()) {
			return this.validar(result);
		}

		Optional<Curso> o = service.findById(id);
		if (o.isPresent()) {
			Curso cursoDb = o.get();
			cursoDb.setNombre(curso.getNombre());
			Curso cursoUpdate = service.save(cursoDb);
			return ResponseEntity.status(HttpStatus.CREATED).body(cursoUpdate);
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}/asignar-alumnos")
	public ResponseEntity<?> asignarAlumnos(@RequestBody List<Alumno> alumnos, @PathVariable Long id) {
		Optional<Curso> o = service.findById(id);
		if (o.isPresent()) {

			Curso dbCurso = o.get();
			alumnos.forEach(a -> {
				dbCurso.addAlummnos(a);
			});

			Curso cursoUpdate = service.save(dbCurso);
			return ResponseEntity.status(HttpStatus.CREATED).body(cursoUpdate);
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}/eliminar-alumno")
	public ResponseEntity<?> eliminarAlumno(@RequestBody Alumno alumno, @PathVariable Long id) {
		Optional<Curso> o = service.findById(id);
		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Curso dbCurso = o.get();
		dbCurso.removeAlumnos(alumno);
		Curso cursoUpdate = service.save(dbCurso);
		return ResponseEntity.status(HttpStatus.CREATED).body(cursoUpdate);
	}

	@GetMapping("/alumno/{id}")
	public ResponseEntity<?> buscarPorAlumnoId(@PathVariable Long id) {
		Curso curso = service.findCursoByAlumnoId(id);
		return ResponseEntity.ok(curso);
	}

	@PutMapping("/{id}/asignar-examenes")
	public ResponseEntity<?> asignarExamenes(@RequestBody List<Examen> examenes, @PathVariable Long id) {
		Optional<Curso> o = service.findById(id);
		if (o.isPresent()) {

			Curso dbCurso = o.get();
			examenes.forEach(e -> {
				dbCurso.addExamenes(e);
			});

			Curso cursoUpdate = service.save(dbCurso);
			return ResponseEntity.status(HttpStatus.CREATED).body(cursoUpdate);
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}/eliminar-examen")
	public ResponseEntity<?> eliminarExamen(@RequestBody Examen examen, @PathVariable Long id) {
		Optional<Curso> o = service.findById(id);
		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Curso dbCurso = o.get();
		dbCurso.removeExamen(examen);
		Curso cursoUpdate = service.save(dbCurso);
		return ResponseEntity.status(HttpStatus.CREATED).body(cursoUpdate);
	}

}
