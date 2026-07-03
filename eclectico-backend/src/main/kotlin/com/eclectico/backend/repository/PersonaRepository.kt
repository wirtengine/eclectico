package com.eclectico.backend.repository

import com.eclectico.backend.entity.Persona
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PersonaRepository : JpaRepository<Persona, UUID> {
    fun findByEmail(email: String): Persona?
    fun findByTelefono(telefono: String): Persona?
    fun findAllByTipo(tipo: Char): List<Persona>

    fun findByTipoAndNombreContainingIgnoreCaseOrTelefonoContaining(
        tipo: Char,
        nombre: String,
        telefono: String
    ): List<Persona>
}