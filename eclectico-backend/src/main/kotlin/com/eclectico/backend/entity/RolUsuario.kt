package com.eclectico.backend.entity

import jakarta.persistence.*

@Entity
@Table(name = "rol_usuario")
data class RolUsuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idRol: Int? = null,

    @Column(nullable = false, unique = true, length = 30)
    val nombre: String
)