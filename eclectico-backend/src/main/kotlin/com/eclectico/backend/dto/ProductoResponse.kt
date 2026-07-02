package com.eclectico.backend.dto

import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class ProductoResponse(
    val idProducto: UUID,
    val codigo: String,
    val descripcion: String,
    val linea: String,
    val costo: BigDecimal,
    val precioVenta: BigDecimal,
    val precioInicial: BigDecimal,
    val talla: String?,
    val medidas: String?,
    val color: String?,
    val marcaOriginal: String?,
    val estadoNotas: String?,
    val estado: String,
    val fechaCompra: LocalDate?,
    val fechaPublicacion: LocalDate?,
    val diasParaRebaja: Short?,
    val proveedor: String?,
    val imagenPrincipal: String?,
    val imagenes: List<String>?,
    val creadoPor: String?,
    val creadoEn: String?
)