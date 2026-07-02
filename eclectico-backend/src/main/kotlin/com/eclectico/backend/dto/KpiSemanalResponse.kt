package com.eclectico.backend.dto

import java.math.BigDecimal
import java.time.LocalDate

data class KpiSemanalResponse(
    val semanaInicio: LocalDate,
    val prendasVendidas: Long,
    val ingresos: BigDecimal?,
    val costos: BigDecimal?,
    val margenBruto: BigDecimal?,
    val utilidadNeta: BigDecimal?
)