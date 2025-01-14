package com.tz.uandes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tz.uandes.dto.AlumnoDTO;

@Service
public class AlumnoService {

	@Autowired
	RestTemplate rest;
	
	String ruta;
	
    public List<AlumnoDTO> listar(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        ruta = "http://localhost:8020/api/alumnos/listar";
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<List<AlumnoDTO>> response = rest.exchange(ruta, HttpMethod.GET, request, new ParameterizedTypeReference<List<AlumnoDTO>>() {});
        
        return response.getBody();
    }
}
