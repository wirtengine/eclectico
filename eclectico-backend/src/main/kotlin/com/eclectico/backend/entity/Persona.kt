package com.eclectico.backend.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "persona")
data class Persona(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val idPersona: UUID? = null,

    @Column(nullable = false, length = 100)
    val nombre: String,

    @Column(unique = true, length = 15)
    val telefono: String? = null,

    @Column(unique = true, length = 100)
    val email: String? = null,

    @Column(length = 250)
    val direccion: String? = null,

    @Column(nullable = false, columnDefinition = "CHAR(1)")
    val tipo: Char,  // 'C', 'S', 'R'

    @Column(name = "password_hash", columnDefinition = "TEXT")
    val passwordHash: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol")
    val rol: RolUsuario? = null,

    @Column(name = "total_compras", precision = 12, scale = 2)
    val totalCompras: BigDecimal? = BigDecimal.ZERO,

    @Column(name = "num_compras")
    val numCompras: Int? = 0,

    @Column(name = "ultima_compra")
    val ultimaCompra: LocalDate? = null,

    @Column(columnDefinition = "CHAR(1) DEFAULT 'N'")
    val segmento: Char? = 'N',

    @Column(name = "fecha_registro")
    val fechaRegistro: LocalDate? = LocalDate.now(),

    val activo: Boolean? = true
)