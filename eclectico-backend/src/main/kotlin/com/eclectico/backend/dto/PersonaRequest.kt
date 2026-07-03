package com.eclectico.backend.dto

data class PersonaRequest(
    val nombre: String,
    val telefono: String?,
    val email: String?,
    val direccion: String?
)