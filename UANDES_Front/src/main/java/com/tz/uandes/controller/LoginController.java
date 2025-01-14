package com.tz.uandes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tz.uandes.dto.AlumnoDTO;
import com.tz.uandes.dto.AuthLoginRequest;
import com.tz.uandes.services.AlumnoService;
import com.tz.uandes.services.UsuarioService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class LoginController {

	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
    AlumnoService alumnoService;
	
	//Sin esto la variable no se encuentra en el index y da error 500.
	@GetMapping("/")
	public String index(Model model, @CookieValue(value = "authToken", defaultValue = "") String token) {
	    model.addAttribute("EstaLogeado", !token.isEmpty());
	    return "index"; 
	}
	
	@GetMapping("/auth/login")
	public String login(Model model, @CookieValue(value = "authToken", defaultValue = "") String token) {
	    model.addAttribute("EstaLogeado", !token.isEmpty());
	    //Si el usuario esta logeado lo redirige a index
	    if (!token.isEmpty()) {
	        return "redirect:/";
	    }
	    return "login";
	}

    @PostMapping("/auth/login")
    public String processLogin(@ModelAttribute @Validated AuthLoginRequest loginRequest, HttpServletResponse response) {
        try {
            String token = usuarioService.signin(loginRequest);
            System.out.println("Token recibido: " + token); 
            
            // Manejo de la cookie para el token
            Cookie tokenCookie = new Cookie("authToken", token);
            tokenCookie.setHttpOnly(true); // Solo http para mas seguridad
            tokenCookie.setPath("/"); // Cookie para toda la aplicacion.
            tokenCookie.setMaxAge(7 * 24 * 60 * 60); // 7 dias para la expiracion
            response.addCookie(tokenCookie);

            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace(); 
            return "redirect:/auth/login?error=true"; //Redireccion a error si es que los datos son incorrectos
        }
    }
	
    @GetMapping("/auth/logout")
    public String logout(HttpServletResponse response) {
        // Invalida la cookie
        Cookie cookie = new Cookie("authToken", null);
        cookie.setPath("/");
        cookie.setMaxAge(0); 
        response.addCookie(cookie);
        
        return "redirect:/"; 
    }
	
	@GetMapping("/home")
	public String home(Model model, @CookieValue(value = "authToken", defaultValue = "") String token) {
	    if (token.isEmpty()) {
	        return "redirect:/auth/login"; // Si no se encuenta el token se redirige. Manejo de autorizacion manual mediante la verificacion del JWT
	    }
	    model.addAttribute("EstaLogeado", true);
	    List<AlumnoDTO> alumnos = alumnoService.listar(token);
	    model.addAttribute("alumnos", alumnos);
	    return "home";
	}
}
