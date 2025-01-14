package com.tz.uandes.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tz.uandes.model.Alumno;
import com.tz.uandes.model.Materia;
import com.tz.uandes.repository.IAlumnoRepository;
import com.tz.uandes.repository.IMateriaRepository;

import jakarta.transaction.Transactional;

@Service
public class AlumnoService {

	@Autowired
	IAlumnoRepository Arepo;
	
	@Autowired
    IMateriaRepository Mrepo; 

    @Transactional
    public Alumno save(Alumno alumno) {
        Set<Materia> materias = new HashSet<>();
        for (Materia materia : alumno.getMateriaList()) {
            Materia existingMateria = Mrepo.findById(materia.getId())
                .orElseThrow(() -> new RuntimeException("No existe Materia con el id: " + materia.getId()));
            materias.add(existingMateria);
        }
        alumno.setMateriaList(materias); 
        return Arepo.save(alumno);
    }
	
	public List<Alumno> listarAlumno(){
		return Arepo.findAll();
	}
}
