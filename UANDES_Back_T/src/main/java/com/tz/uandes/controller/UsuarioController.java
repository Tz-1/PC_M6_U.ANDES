
package com.tz.uandes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tz.uandes.model.Usuario;
import com.tz.uandes.security.dto.AuthLoginRequest;
import com.tz.uandes.security.dto.AuthResponse;
import com.tz.uandes.security.service.UserDetailsServiceImpl;

@RestController
@RequestMapping("/auth")
public class UsuarioController {
		
	@Autowired
	UserDetailsServiceImpl userDetailService;

	@PostMapping("/signup")
    public ResponseEntity<Usuario> signup(@RequestBody Usuario usuario) {
        System.out.println("<CONTROLLER> Se va a registrar el siguiente usuario: " + usuario.getUsername());
        Usuario createdUser = userDetailService.signup(usuario);
        System.out.println("<CONTROLLER> Usuario registrado con éxito: " + createdUser.getUsername());
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody AuthLoginRequest loginRequest) {
        System.out.println("<CONTROLLER> Se va a realizar el login para el usuario para saber su rol: " + loginRequest.username());
        AuthResponse response = userDetailService.loginUser(loginRequest);
        System.out.println("<CONTROLLER> El usuario se logeado con exito: : " + loginRequest.username());
        return ResponseEntity.ok(response);
    }
}
