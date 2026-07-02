package com.eclectico.backend.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "persona")
@JsonIgnoreProperties(value = ["hibernateLazyInitializer", "handler"])
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
    var tipo: Char,   // ← cambiamos a var

    @Column(name = "password_hash", columnDefinition = "TEXT")
    var passwordHash: String? = null,   // ← cambiamos a var

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol")
    var rol: RolUsuario? = null,   // ← cambiamos a var

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