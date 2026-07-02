package com.eclectico.backend.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "proveedor")
data class Proveedor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idProveedor: Int? = null,

    @Column(nullable = false, length = 100)
    val nombre: String,

    @Column(length = 15)
    val telefono: String? = null,

    @Column(columnDefinition = "TEXT")
    val ubicacion: String? = null,

    @Column(name = "fecha_ultima_visita")
    val fechaUltimaVisita: LocalDate? = null,

    val calificacion: Int? = null
)