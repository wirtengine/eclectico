package com.eclectico.backend.dto

import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class PersonaResponse(
    val idPersona: UUID,
    val nombre: String,
    val telefono: String?,
    val email: String?,
    val tipo: Char,
    val totalCompras: BigDecimal?,
    val numCompras: Int?,
    val ultimaCompra: LocalDate?,
    val segmento: Char?,
    val fechaRegistro: LocalDate?,
    val activo: Boolean?
)