package com.eclectico.backend.entity

import jakarta.persistence.*

@Entity
@Table(name = "metodo_pago")
data class MetodoPago(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idMetodo: Int? = null,

    @Column(nullable = false, unique = true, length = 30)
    var nombre: String   // ← ahora var
)