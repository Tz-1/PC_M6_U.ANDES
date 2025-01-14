package com.tz.uandes.utils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;


@Component
public class JwtUtils {

	@Value("${security.key}")
	private String privateKey;
	
	@Value("${security.user}")
	private String userGenerator;
	

	public String createToken(Authentication authentication) {
		Algorithm algo = Algorithm.HMAC256(privateKey);
		String username = authentication.getPrincipal().toString();
		String authorities = authentication.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		
		//Autoridades y fecha de expiracion
		String jwtToken = JWT.create()
				.withIssuer(userGenerator)
				.withSubject(username)
				.withClaim("authorities", authorities)
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis()+5000000))
				.withJWTId(UUID.randomUUID().toString())
				.withNotBefore(new Date(System.currentTimeMillis()))
				.sign(algo);
		
		return jwtToken;
	}
	
	public DecodedJWT validateToken(String token) {
		try {
			Algorithm algo = Algorithm.HMAC256(privateKey);
			JWTVerifier verifier = JWT.require(algo)
					.withIssuer(userGenerator)
					.build();
			DecodedJWT decoded = verifier.verify(token);
			return decoded;
		} catch (JWTVerificationException exception) {
			throw new JWTVerificationException("Token Invalido");
		}
	}
	
	public String extractUserName(DecodedJWT decoded) {
		return decoded.getSubject().toLowerCase();
	}
	
	public Claim getSpecifiedClaim(DecodedJWT decoded, String claimName) {
		return decoded.getClaim(claimName);
	}
	
	public Map<String, Claim> getAllClaims(DecodedJWT decoded){
		return decoded.getClaims();
	}
}
