package com.eclectico.backend.entity

import jakarta.persistence.*

@Entity
@Table(name = "configuracion")
data class Configuracion(
    @Id
    @Column(length = 50)
    val clave: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    var valor: String
)