package com.eclectico.backend.dto

import java.math.BigDecimal

data class GastoRequest(
    val concepto: String,
    val monto: BigDecimal,
    val fecha: String?,
    val idSocio: String?,
    val comprobanteUrl: String?
)