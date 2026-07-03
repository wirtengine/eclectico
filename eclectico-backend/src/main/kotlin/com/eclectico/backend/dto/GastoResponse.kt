package com.eclectico.backend.dto

import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class GastoResponse(
    val idGasto: UUID,
    val fecha: LocalDate,
    val concepto: String,
    val monto: BigDecimal,
    val socio: String?,
    val comprobanteUrl: String?
)