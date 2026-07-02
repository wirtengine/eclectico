package com.eclectico.backend.dto

import java.math.BigDecimal
import java.util.UUID

data class ProductoRecomendadoResponse(
    val idProducto: UUID,
    val descripcion: String,
    val precio: BigDecimal?,
    val motivo: String?
)