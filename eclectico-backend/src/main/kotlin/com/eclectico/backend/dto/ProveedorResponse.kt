package com.eclectico.backend.dto

data class ProveedorResponse(
    val idProveedor: Int,
    val nombre: String,
    val telefono: String?,
    val ubicacion: String?,
    val fechaUltimaVisita: String?,
    val calificacion: Short?
)