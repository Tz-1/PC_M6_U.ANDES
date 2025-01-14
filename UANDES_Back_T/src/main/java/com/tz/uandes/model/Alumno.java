package com.tz.uandes.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name="alumnos")
public class Alumno {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=15, nullable=false, unique=true)
	private String rut;
	
	@Column(length=100, nullable=false)
	private String nombre;
	
	@Column(length=150, nullable=false)
	private String direccion;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name="alumnos_materias", joinColumns = @JoinColumn(name="alumno_id"), inverseJoinColumns = @JoinColumn(name="materia_id"))
	private Set<Materia> materiaList = new HashSet<>();
	
}
