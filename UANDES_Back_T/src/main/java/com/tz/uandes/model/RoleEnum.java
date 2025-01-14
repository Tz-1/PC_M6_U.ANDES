package com.tz.uandes.model;

import org.springframework.security.core.GrantedAuthority;


public enum RoleEnum implements GrantedAuthority {
	ROLE_ADMIN, ROLE_CLIENT;
	
	public String getAuthority() {
		return name();
	}
}
