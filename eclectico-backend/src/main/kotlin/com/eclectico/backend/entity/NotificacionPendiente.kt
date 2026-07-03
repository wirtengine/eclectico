package com.eclectico.backend.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "notificacion_pendiente")
data class NotificacionPendiente(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    val cliente: Persona,

    @Column(nullable = false, length = 20)
    val tipo: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val mensaje: String,

    @Column(name = "creado_en")
    val creadoEn: LocalDateTime? = LocalDateTime.now(),

    var enviado: Boolean? = false
)