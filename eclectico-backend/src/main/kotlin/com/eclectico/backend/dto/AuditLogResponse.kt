package com.eclectico.backend.dto

import java.time.LocalDateTime

data class AuditLogResponse(
    val idAudit: Long,
    val tablaAfectada: String,
    val operacion: String,
    val idRegistro: String,
    val usuario: String,
    val fecha: LocalDateTime?,
    val datosAnteriores: Any?,
    val datosNuevos: Any?
)