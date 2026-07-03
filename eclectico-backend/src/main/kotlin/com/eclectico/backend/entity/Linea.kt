package com.eclectico.backend.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*

@Entity
@Table(name = "linea")
@JsonIgnoreProperties(value = ["hibernateLazyInitializer", "handler"])
data class Linea(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idLinea: Int? = null,

    @Column(nullable = false, unique = true, length = 20)
    var nombre: String,

    var descripcion: String? = null
)