package com.eclectico.backend.dto

import java.time.LocalDateTime
import java.util.UUID

data class NotificacionResponse(
    val id: UUID,
    val cliente: String,
    val tipo: String,
    val mensaje: String,
    val creadoEn: LocalDateTime?,
    val enviado: Boolean
)