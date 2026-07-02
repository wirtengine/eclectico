package com.eclectico.backend.dto

import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class OfertaRelampagoResponse(
    val idProducto: UUID,
    val codigo: String,
    val descripcion: String,
    val linea: String,
    val talla: String?,
    val medidas: String?,
    val precioVenta: BigDecimal,
    val costo: BigDecimal,
    val margen: BigDecimal,
    val precioOfertaSugerido: BigDecimal,
    val descuentoPct: BigDecimal,
    val imagenPrincipal: String?,
    val estadoNotas: String?,
    val fechaPublicacion: LocalDate?
)