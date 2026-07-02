package com.eclectico.backend.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal
import java.util.UUID

data class VentaRequest(
    @field:NotNull(message = "El cliente es obligatorio")
    val idCliente: UUID,

    val idVendedor: UUID? = null,

    @field:NotNull(message = "El método de pago es obligatorio")
    val idMetodoPago: Int,

    val estadoEntrega: String = "Pendiente",

    val notas: String? = null,

    @field:NotEmpty(message = "Debe incluir al menos un producto")
    val detalles: List<@Valid DetalleVentaRequest>
)

data class DetalleVentaRequest(
    @field:NotNull(message = "El producto es obligatorio")
    val idProducto: UUID,

    @field:NotNull(message = "El precio de venta es obligatorio")
    @field:Positive(message = "El precio debe ser positivo")
    val precioVentaReal: BigDecimal,

    val descuentoAplicado: BigDecimal = BigDecimal.ZERO
)