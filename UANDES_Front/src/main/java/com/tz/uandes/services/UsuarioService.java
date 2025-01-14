package com.tz.uandes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tz.uandes.dto.AuthLoginRequest;
import com.tz.uandes.dto.AuthResponse;

@Service
public class UsuarioService {

    @Autowired
    RestTemplate rest;

    String ruta = "http://localhost:8020/auth/signin"; 

    public String signin(AuthLoginRequest loginRequest) throws JsonMappingException, JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AuthLoginRequest> request = new HttpEntity<>(loginRequest, headers);

        ResponseEntity<String> response = rest.exchange(ruta, HttpMethod.POST, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
        	//Object mapper debido a que el token que me devolvia me lo mandaba en formato JSON, pero ahora lo deserializa y lo manda como objeto para su uso
            ObjectMapper objectMapper = new ObjectMapper();
            AuthResponse authResponse = objectMapper.readValue(response.getBody(), AuthResponse.class);
            
            return authResponse.jwt(); // Return del token
        } else {
            throw new RuntimeException("Failed to sign in: " + response.getStatusCode());
        }
    }
}