package com.eclectico.backend.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "producto")
data class Producto(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val idProducto: UUID? = null,

    @Column(nullable = false, unique = true, length = 10)
    val codigo: String? = null, // generado por trigger

    @Column(nullable = false, columnDefinition = "TEXT")
    val descripcion: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_linea", nullable = false)
    val linea: Linea,

    @Column(nullable = false, precision = 10, scale = 2)
    val costo: BigDecimal,

    @Column(name = "precio_venta", nullable = false, precision = 10, scale = 2)
    val precioVenta: BigDecimal,

    @Column(name = "precio_inicial", nullable = false, precision = 10, scale = 2)
    val precioInicial: BigDecimal? = null,

    @Column(length = 10)
    val talla: String? = null,

    @Column(columnDefinition = "TEXT")
    val medidas: String? = null,

    @Column(length = 30)
    val color: String? = null,

    @Column(name = "marca_original", length = 100)
    val marcaOriginal: String? = null,

    @Column(name = "estado_notas", columnDefinition = "TEXT")
    val estadoNotas: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado", nullable = false)
    val estado: EstadoProducto,

    @Column(name = "fecha_compra")
    val fechaCompra: LocalDate? = LocalDate.now(),

    @Column(name = "fecha_publicacion")
    val fechaPublicacion: LocalDate? = null,

    @Column(name = "fecha_venta")
    val fechaVenta: LocalDate? = null,

    @Column(name = "dias_para_rebaja")
    val diasParaRebaja: Int? = 10,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor")
    val proveedor: Proveedor? = null,

    @Column(name = "imagen_principal", columnDefinition = "TEXT")
    val imagenPrincipal: String? = null,

    @Column(name = "imagenes", columnDefinition = "TEXT[]")
    val imagenes: Array<String>? = null, // PostgreSQL array

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creado_por")
    val creadoPor: Persona? = null,

    @Column(name = "creado_en")
    val creadoEn: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "actualizado_en")
    val actualizadoEn: LocalDateTime? = LocalDateTime.now()
)