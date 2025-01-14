package com.tz.uandes.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=100, nullable=false)
	private String name;
	
	@Column(length=100, nullable=false)
	private String username;
	
	@Column(length=150, nullable=false, unique=true)
	private String email;
	
	@Column(nullable=false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	private List<RoleEnum> roles = new ArrayList<>();
	
	@Column(name="is_expired")
	private boolean isEnabled;
	@Column(name="account_no_expired")
	private boolean accountNoExpired;
	@Column(name="account_no_locked")
	private boolean accountNoLocked;
	@Column(name="credential_no_expired")
	private boolean credentialNoExpired;

}
