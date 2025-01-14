package com.tz.uandes.security;

import java.io.IOException;
import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.tz.uandes.utils.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator extends OncePerRequestFilter{

	private JwtUtils jwtUtil;
	
	public JwtTokenValidator(JwtUtils jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request, 
			@NonNull HttpServletResponse response, 
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		
		String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if(jwtToken != null) {
			jwtToken = jwtToken.substring(7);
			DecodedJWT decoded = jwtUtil.validateToken(jwtToken);
			
			String username = jwtUtil.extractUserName(decoded);
			String Stringauthorities = jwtUtil.getSpecifiedClaim(decoded, "authorities").asString();
			
			Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(Stringauthorities);
			
			SecurityContext context = SecurityContextHolder.getContext();
			Authentication auth = new UsernamePasswordAuthenticationToken(username,null,authorities);
			
			context.setAuthentication(auth);
			SecurityContextHolder.setContext(context);
		}
		filterChain.doFilter(request, response);
	}
}
