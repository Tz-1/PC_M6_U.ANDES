package com.tz.uandes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tz.uandes.model.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario,Long>{
	//Optional para encontrar el usuario por email ya que es unico.
	Optional<Usuario> findByEmail(String email);
}
