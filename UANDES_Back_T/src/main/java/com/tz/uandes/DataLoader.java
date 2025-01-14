package com.tz.uandes;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.tz.uandes.model.Alumno;
import com.tz.uandes.model.Materia;
import com.tz.uandes.model.RoleEnum;
import com.tz.uandes.model.Usuario;
import com.tz.uandes.repository.IAlumnoRepository;
import com.tz.uandes.repository.IMateriaRepository;
import com.tz.uandes.repository.IUsuarioRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    IUsuarioRepository repo;

    @Autowired
    PasswordEncoder passEncode;

    @Autowired
    IAlumnoRepository alumnoRepo;

    @Autowired
    IMateriaRepository mateRepo;

    //Creacion de cuentas con diferentes Roles y estudiantes al inicio del programa para su testeo en front y a traves de Postman.
    @Override
    public void run(String... args) throws Exception {   	
	    createUsers();    
	    createStudents();
	   
    }
   
    private void createUsers() {
    	System.out.println("<DATA> Creando Usuarios");
    	if (repo.findByEmail("tz@tz.cl").isEmpty()) {
            List<RoleEnum> clientRoles = Arrays.asList(RoleEnum.ROLE_CLIENT);
            Usuario clientUser   = new Usuario(null, "tz", "tz", "tz@tz.cl", passEncode.encode("tb123"), clientRoles, false, true, true, true);
            repo.save(clientUser);
            System.out.println("<DATA> Usuario con rol de cliente creado con exito.");
        } else {
        	//Aviso en vez de error personalizado, ya que este pararia el proyecto de ejecutarse.
        	System.out.println("<DATA> Usuario cliente ya existe");
        }

        if (repo.findByEmail("admin@tz.cl").isEmpty()) {
            List<RoleEnum> adminRoles = Arrays.asList(RoleEnum.ROLE_ADMIN);
            Usuario adminUser   = new Usuario(null, "admin", "admin", "admin@tz.cl", passEncode.encode("admin123"), adminRoles, false, true, true, true);
            repo.save(adminUser);
            System.out.println("<DATA> Usuario con rol de admin, creado con exito.");
        } else {
        	System.out.println("<DATA> Usuario admin ya existe");
        }
    }
    
    private void createStudents() {
    	System.out.println("<DATA> Creando estudiantes junto con materias.");
    	//if para evitar la creacion al ver que no hay datos de estudiantes en la base de datos.
    	if (alumnoRepo.count() == 0) {
	  	  Materia quimica = Materia.builder().nombre("Quimica").build();
	  	  Materia mate = Materia.builder().nombre("Matematicas").build();
	  	  
	  	  Alumno miku = Alumno.builder().rut("123456789-0").nombre("Miku").direccion("Peor es nada 123").materiaList(Set.of(quimica,mate)).build();
	  	  Alumno teto = Alumno.builder().rut("987654321-0").nombre("Teto").direccion("Cumpeo 654").materiaList(Set.of(mate)).build();
	  	  
	  	  alumnoRepo.saveAll(List.of(miku,teto)); 
	  	  
	  	  System.out.println("<DATA> Creacion de estudiantes y materias con exito");
    	} else {
    		System.out.println("<DATA> Ya existen datos de estudiantes.");
    	}
    }
    
}