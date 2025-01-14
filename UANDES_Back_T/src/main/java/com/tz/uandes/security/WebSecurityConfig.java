package com.tz.uandes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.tz.uandes.security.service.UserDetailsServiceImpl;
import com.tz.uandes.utils.JwtUtils;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	JwtUtils jwtUtil;
	
	//Configuracion de filtros
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
		.csrf(csrf -> csrf.disable())
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
		.authorizeHttpRequests(http -> {
			http.requestMatchers(HttpMethod.GET, "/api/alumnos/listar").hasAnyAuthority("ROLE_CLIENT","ROLE_ADMIN");
            http.requestMatchers(HttpMethod.POST, "/api/alumnos/guardar").hasAnyAuthority("ROLE_ADMIN"); 
            http.requestMatchers(HttpMethod.POST, "/api/materias/guardar").hasAnyAuthority("ROLE_ADMIN");
            http.requestMatchers(HttpMethod.POST, "/auth/signup").permitAll();
            http.requestMatchers(HttpMethod.POST, "/auth/signin").permitAll();

		})
		.formLogin(formlogin -> formlogin
				.loginPage("/auth/login")
				.loginProcessingUrl("/auth/login")
				)
		.httpBasic(Customizer.withDefaults())
		.addFilterBefore(new JwtTokenValidator(jwtUtil), BasicAuthenticationFilter.class)
		;
		
		return httpSecurity.build();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailService) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailService);
		return provider;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
