package com.eclectico.backend.entity

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import java.math.BigDecimal
import java.sql.Types
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
    val codigo: String? = null, // generado por trigger, no se modifica

    @Column(nullable = false, columnDefinition = "TEXT")
    var descripcion: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_linea", nullable = false)
    var linea: Linea,

    @Column(nullable = false, precision = 10, scale = 2)
    var costo: BigDecimal,

    @Column(name = "precio_venta", nullable = false, precision = 10, scale = 2)
    var precioVenta: BigDecimal,

    @Column(name = "precio_inicial", nullable = false, precision = 10, scale = 2)
    val precioInicial: BigDecimal? = null, // no se modifica, se mantiene original

    @Column(length = 10)
    var talla: String? = null,

    @Column(columnDefinition = "TEXT")
    var medidas: String? = null,

    @Column(length = 30)
    var color: String? = null,

    @Column(name = "marca_original", length = 100)
    var marcaOriginal: String? = null,

    @Column(name = "estado_notas", columnDefinition = "TEXT")
    var estadoNotas: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado", nullable = false)
    var estado: EstadoProducto, // el estado no se modifica directamente en actualizarProducto

    @Column(name = "fecha_compra")
    val fechaCompra: LocalDate? = LocalDate.now(),

    @Column(name = "fecha_publicacion")
    val fechaPublicacion: LocalDate? = null,

    @Column(name = "fecha_venta")
    val fechaVenta: LocalDate? = null,

    @Column(name = "dias_para_rebaja")
    val diasParaRebaja: Short? = 10,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor")
    var proveedor: Proveedor? = null,

    @Column(name = "imagen_principal", columnDefinition = "TEXT")
    var imagenPrincipal: String? = null,

    @JdbcTypeCode(Types.ARRAY)
    @Column(name = "imagenes", columnDefinition = "text[]")
    var imagenes: List<String>? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creado_por")
    val creadoPor: Persona? = null,

    @Column(name = "creado_en")
    val creadoEn: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "actualizado_en")
    val actualizadoEn: LocalDateTime? = LocalDateTime.now()
)