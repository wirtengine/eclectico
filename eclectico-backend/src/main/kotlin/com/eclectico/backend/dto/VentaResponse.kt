package com.eclectico.backend.dto

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class VentaResponse(
    val idVenta: UUID,
    val fechaVenta: LocalDateTime,
    val idCliente: UUID,
    val nombreCliente: String,
    val montoTotal: BigDecimal,
    val costoTotal: BigDecimal,
    val margenBruto: BigDecimal,
    val metodoPago: String?,
    val estadoEntrega: String,
    val vendedor: String?,
    val notas: String?,
    val detalles: List<DetalleVentaResponse>
)

data class DetalleVentaResponse(
    val idProducto: UUID,
    val codigoProducto: String,
    val descripcionProducto: String,
    val precioVentaReal: BigDecimal,
    val descuentoAplicado: BigDecimal
)