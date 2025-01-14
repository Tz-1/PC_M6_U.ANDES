package com.tz.uandes.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AlumnoDTO(@NotNull Long id, @NotBlank String rut, @NotBlank String nombre, @NotBlank String direccion, @NotNull Set<MateriaDTO> materiaList ) {

}
