package com.eclectico.backend.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "venta")
data class Venta(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val idVenta: UUID? = null,

    @Column(name = "fecha_venta", nullable = false)
    val fechaVenta: LocalDateTime? = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    val cliente: Persona,

    @Column(name = "monto_total", nullable = false, precision = 10, scale = 2)
    val montoTotal: BigDecimal,

    @Column(name = "costo_total", nullable = false, precision = 10, scale = 2)
    val costoTotal: BigDecimal,

    @Column(name = "margen_bruto", insertable = false, updatable = false)
    val margenBruto: BigDecimal? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_metodo_pago")
    val metodoPago: MetodoPago? = null,

    @Column(name = "estado_entrega", length = 20)
    var estadoEntrega: String? = "Pendiente",   // ← ahora es var

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendedor")
    val vendedor: Persona? = null,

    @Column(columnDefinition = "TEXT")
    val notas: String? = null,

    @Column(name = "creado_en")
    val creadoEn: LocalDateTime? = LocalDateTime.now()
)