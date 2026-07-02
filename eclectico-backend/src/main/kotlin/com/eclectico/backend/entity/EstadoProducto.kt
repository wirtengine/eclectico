package com.eclectico.backend.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*

@Entity
@Table(name = "estado_producto")
@JsonIgnoreProperties(value = ["hibernateLazyInitializer", "handler"])
data class EstadoProducto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idEstado: Int? = null,

    @Column(nullable = false, unique = true, length = 20)
    val nombre: String
)