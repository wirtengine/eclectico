package com.eclectico.backend.dto

import jakarta.validation.constraints.NotBlank

data class SocioRequest(
    @field:NotBlank(message = "El nombre es obligatorio")
    val nombre: String,
    @field:NotBlank(message = "El email es obligatorio")
    val email: String,
    val telefono: String? = null,
    val password: String? = null,
    val idRol: Int? = null
)