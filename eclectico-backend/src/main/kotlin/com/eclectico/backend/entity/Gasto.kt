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
    var idGasto: UUID? = null,

    @Column(nullable = false)
    var fecha: LocalDate? = LocalDate.now(),

    @Column(nullable = false, length = 200)
    var concepto: String,

    @Column(nullable = false, precision = 10, scale = 2)
    var monto: BigDecimal,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_socio")
    var socio: Persona? = null,

    @Column(name = "comprobante_url", columnDefinition = "TEXT")
    var comprobanteUrl: String? = null,

    @Column(name = "creado_en")
    var creadoEn: LocalDateTime? = LocalDateTime.now()
)