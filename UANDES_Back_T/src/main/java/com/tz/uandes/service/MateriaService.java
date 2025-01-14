package com.tz.uandes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tz.uandes.model.Materia;
import com.tz.uandes.repository.IMateriaRepository;

@Service
public class MateriaService {

	@Autowired
	IMateriaRepository Mrepo;

	public Materia guardarMateria(Materia m) {
		System.out.println("<SERVICE> Intentando guardar Materia: " + m);
		if (m == null) {
			throw new Error("La materia no puede ser nula");
		}
		Materia savedMateria = Mrepo.save(m);
	    System.out.println("<SERVICE> Materia guardada en base de datos con id: " + savedMateria.getId());
		return savedMateria;
	}
}
