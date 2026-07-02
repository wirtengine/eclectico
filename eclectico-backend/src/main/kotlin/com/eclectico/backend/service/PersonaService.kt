package com.eclectico.backend.service

import com.eclectico.backend.dto.PersonaResponse
import com.eclectico.backend.dto.toResponse
import com.eclectico.backend.entity.Persona
import com.eclectico.backend.repository.PersonaRepository
import org.springframework.stereotype.Service

@Service
class PersonaService(private val personaRepository: PersonaRepository) {

    fun buscarPorEmail(email: String): Persona? = personaRepository.findByEmail(email)

    fun buscarPorTelefono(telefono: String): PersonaResponse? {
        val persona = personaRepository.findByTelefono(telefono)
        return persona?.toResponse()
    }

    fun guardar(persona: Persona): PersonaResponse = personaRepository.save(persona).toResponse()
}