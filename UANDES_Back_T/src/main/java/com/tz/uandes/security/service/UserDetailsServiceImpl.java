package com.tz.uandes.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tz.uandes.model.Usuario;
import com.tz.uandes.repository.IUsuarioRepository;
import com.tz.uandes.security.dto.AuthLoginRequest;
import com.tz.uandes.security.dto.AuthResponse;
import com.tz.uandes.utils.JwtUtils;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUsuarioRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    // Método requerido por UserDetailsService
    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    	//Utilizo findByEmail en vez de Username, debido a la propiedad unica de email.
		Usuario userEntity = userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuario No encontrado"));
		
		List<SimpleGrantedAuthority> authority = new ArrayList<>();
		userEntity.getRoles().forEach(role -> authority.add(new SimpleGrantedAuthority(role.name())));
		
		return new User(userEntity.getEmail(),
				userEntity.getPassword(),
				userEntity.isEnabled(),
				userEntity.isCredentialNoExpired(),
				userEntity.isAccountNoLocked(),
				userEntity.isCredentialNoExpired(),
				authority);
		
	}

    // Método para registrar un usuario
    public Usuario signup(Usuario usuario) {
        if (userRepo.findByEmail(usuario.getEmail()).isPresent()) {
            throw new Error("El usuario ya existe.");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        //Debido a que la creacion de usuario es por POSTMAN, se puede especificar el tipo de rol que se puede tener en el JSON. Tal vez para el front a futuro...
        //usuario.setRoles(List.of(RoleEnum.ROLE_CLIENT));
        return userRepo.save(usuario);
    }

  
    public AuthResponse loginUser(AuthLoginRequest userRequest) {
		String username = userRequest.username();
		String password = userRequest.password();
		
		Authentication auth = authenticate(username, password);
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		String accessToken = jwtUtils.createToken(auth);
		
		AuthResponse authRes = new AuthResponse(username, "User Logged Sucessfully", accessToken, true);
		
		return authRes;
	}
    
    //Metodo de autenticacion para cuando el usuario se logee.
	private Authentication authenticate(String username, String password) {
		UserDetails userDetail = this.loadUserByUsername(username);
		if(userDetail == null) {
			throw new BadCredentialsException("Usuario o clave invalida");
		}
		if(!passwordEncoder.matches(password, userDetail.getPassword())) {
			throw new BadCredentialsException("Clave invalida");
		}
		
		return new UsernamePasswordAuthenticationToken(username, userDetail.getPassword(), userDetail.getAuthorities());
	}
}