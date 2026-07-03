package com.eclectico.backend.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class ProductoRequest(
    @field:NotBlank(message = "La descripción es obligatoria")
    val descripcion: String,

    @field:NotNull(message = "La línea es obligatoria")
    val idLinea: Int,

    @field:NotNull(message = "El costo es obligatorio")
    @field:Positive(message = "El costo debe ser positivo")
    val costo: BigDecimal,

    @field:NotNull(message = "El precio de venta es obligatorio")
    @field:Positive(message = "El precio de venta debe ser positivo")
    val precioVenta: BigDecimal,

    val talla: String? = null,
    val medidas: String? = null,
    val color: String? = null,
    val marcaOriginal: String? = null,
    val estadoNotas: String? = null,
    val idProveedor: Int? = null,
    val imagenPrincipal: String? = null,
    val imagenes: List<String>? = null,
    val idEstado: Int? = null
)