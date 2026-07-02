package com.eclectico.backend.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "detalle_venta")
data class DetalleVenta(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idDetalle: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_venta", nullable = false)
    val venta: Venta,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    val producto: Producto,

    @Column(name = "precio_venta_real", nullable = false, precision = 10, scale = 2)
    val precioVentaReal: BigDecimal,

    @Column(name = "descuento_aplicado", precision = 10, scale = 2)
    val descuentoAplicado: BigDecimal? = BigDecimal.ZERO
)