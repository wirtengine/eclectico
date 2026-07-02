package com.eclectico.backend.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "gasto")
data class Gasto(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val idGasto: UUID? = null,

    @Column(nullable = false)
    val fecha: LocalDate? = LocalDate.now(),

    @Column(nullable = false, length = 200)
    val concepto: String,

    @Column(nullable = false, precision = 10, scale = 2)
    val monto: BigDecimal,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_socio")
    val socio: Persona? = null,

    @Column(name = "comprobante_url", columnDefinition = "TEXT")
    val comprobanteUrl: String? = null,

    @Column(name = "creado_en")
    val creadoEn: LocalDateTime? = LocalDateTime.now()
)