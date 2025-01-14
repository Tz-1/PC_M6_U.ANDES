package com.tz.uandes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MateriaDTO(@NotNull Long id, @NotBlank String nombre) {

}
