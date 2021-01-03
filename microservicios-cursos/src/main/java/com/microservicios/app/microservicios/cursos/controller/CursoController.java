package com.microservicios.app.microservicios.cursos.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.app.common.alumnos.models.entity.Alumno;
import com.microservicios.app.common.examenes.models.entity.Examen;
import com.microservicios.app.commons.controllers.CommonController;
import com.microservicios.app.microservicios.cursos.models.entity.Curso;
import com.microservicios.app.microservicios.cursos.models.entity.CursoAlumno;
import com.microservicios.app.microservicios.cursos.services.CursoService;

@RestController
public class CursoController extends CommonController<Curso, CursoService> {

	@Value("${config.balanceador.test}")
	private String balanceadorTest;

	@GetMapping("/balanceador-test")
	public ResponseEntity<?> balanceadorTest() {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("balanceador", balanceadorTest);
		response.put("cursos", service.findAll());
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/eliminar-alumno/{id}")
	public ResponseEntity<?> eliminarAlumnoCursoPorId(@PathVariable Long id) {
		service.eliminarCursoAlumnoPorId(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	@Override
	public ResponseEntity<?> listar() {
		List<Curso> cursos = ((List<Curso>) service.findAll()).stream().map(c -> {
			c.getCursoAlumnos().forEach(cA -> {
				Alumno alummo = new Alumno();
				alummo.setId(cA.getAlumnoId());
				c.addAlumnos(alummo);
			});
			return c;
		}).collect(Collectors.toList());

		return ResponseEntity.ok().body(cursos);
	}

	@GetMapping("/pagina")
	public ResponseEntity<?> listar(Pageable pageable) {
		Page<Curso> cursos = service.findAll(pageable).map(curso -> {
			curso.getCursoAlumnos().forEach(cA -> {
				Alumno alummo = new Alumno();
				alummo.setId(cA.getAlumnoId());
				curso.addAlumnos(alummo);
			});
			return curso;
		});
		return ResponseEntity.ok().body(cursos);
	}

	@GetMapping("/{id}")
	@Override
	public ResponseEntity<?> ver(@PathVariable Long id) {
		Optional<Curso> o = service.findById(id);
		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Curso curso = o.get();

		if (!curso.getCursoAlumnos().isEmpty()) {
			List<Long> ids = curso.getCursoAlumnos().stream().map(ca -> {
				return ca.getAlumnoId();
			}).collect(Collectors.toList());

			List<Alumno> alumnos = (List<Alumno>) service.obtenerAlumnosPorCurso(ids);

			curso.setAlumnos(alumnos);
		}
		return ResponseEntity.ok().body(curso);
	}

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
				CursoAlumno cursoAlumno = new CursoAlumno();
				cursoAlumno.setAlumnoId(a.getId());
				cursoAlumno.setCurso(dbCurso);
				dbCurso.addCursoAlumno(cursoAlumno);
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
		CursoAlumno cursoAlumno = new CursoAlumno();
		cursoAlumno.setAlumnoId(alumno.getId());
		dbCurso.removeCursoAlumno(cursoAlumno);

		Curso cursoUpdate = service.save(dbCurso);
		return ResponseEntity.status(HttpStatus.CREATED).body(cursoUpdate);
	}

	@GetMapping("/alumno/{id}")
	public ResponseEntity<?> buscarPorAlumnoId(@PathVariable Long id) {
		Curso curso = service.findCursoByAlumnoId(id);

		if (curso != null) {

			List<Long> examenesIds = (List<Long>) service.obtenerExamenesIdsConRespuestasAlumno(id);

			if (examenesIds != null && examenesIds.size() > 0) {
				List<Examen> examenes = curso.getExamenes().stream().map(e -> {
					if (examenesIds.contains(e.getId())) {
						e.setRespondido(true);
					}
					return e;
				}).collect(Collectors.toList());
				curso.setExamenes(examenes);
			}
		}
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
