package com.tz.uandes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tz.uandes.model.Materia;
import com.tz.uandes.service.MateriaService;

@RestController
@RequestMapping("/api/materias")
public class MateriaController {
	
	@Autowired
	MateriaService Mserv;

	@PostMapping("/guardar")
	public ResponseEntity<Materia> createMateria(@RequestBody Materia materia) {
		System.out.println("<CONTROLLER> Se va a guardar la siguiente materia: " + materia.getNombre());
        try {
            Materia savedMateria = Mserv.guardarMateria(materia);
            System.out.println("<CONTROLLER> Se guardo con exito la materia con id: " + savedMateria.getId());
            return new ResponseEntity<>(savedMateria, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
