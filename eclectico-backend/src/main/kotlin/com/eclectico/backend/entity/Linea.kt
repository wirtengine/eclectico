package com.eclectico.backend.entity

import jakarta.persistence.*

@Entity
@Table(name = "linea")
data class Linea(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idLinea: Int? = null,

    @Column(nullable = false, unique = true, length = 20)
    val nombre: String,

    val descripcion: String? = null
)