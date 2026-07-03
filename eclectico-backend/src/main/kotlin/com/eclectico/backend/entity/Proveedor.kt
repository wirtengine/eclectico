package com.eclectico.backend.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "proveedor")
@JsonIgnoreProperties(value = ["hibernateLazyInitializer", "handler"])
data class Proveedor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idProveedor: Int? = null,

    @Column(nullable = false, length = 100)
    var nombre: String,

    @Column(length = 15)
    var telefono: String? = null,

    @Column(columnDefinition = "TEXT")
    var ubicacion: String? = null,

    @Column(name = "fecha_ultima_visita")
    var fechaUltimaVisita: LocalDate? = null,

    var calificacion: Short? = null
)