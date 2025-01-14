package com.tz.uandes.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(@NotBlank String username, @NotBlank String password, List<RoleEnum> roles) {

}
