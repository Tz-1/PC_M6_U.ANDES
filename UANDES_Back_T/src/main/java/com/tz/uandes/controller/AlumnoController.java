package com.tz.uandes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tz.uandes.model.Alumno;
import com.tz.uandes.service.AlumnoService;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {

	@Autowired
	AlumnoService Aserv;
	
	@GetMapping("/listar")
	public ResponseEntity<List<Alumno>> listar(){
		System.out.println("<CONTROLLER> Enviando datos de alumnos");
		return ResponseEntity.ok(Aserv.listarAlumno());
	}
	
	@PostMapping("/guardar")
	public ResponseEntity<Alumno> grabar(@RequestBody Alumno a){
		System.out.println("<CONTROLLER> Se va a guardar el siguiente alumno: " + a.getNombre());
		System.out.println("<CONTROLLER> Materias asociadas: " + a.getMateriaList());
		try {
			Alumno savedAlumno = Aserv.save(a);
			//Dos tipos de avisos o logger
			System.out.println("<CONTROLLER> Se ha guardado con exito el siguiente alumno: " + savedAlumno.getId());
			return new ResponseEntity<>(savedAlumno, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
