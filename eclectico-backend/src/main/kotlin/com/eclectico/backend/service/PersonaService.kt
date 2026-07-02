package com.eclectico.backend.service

import com.eclectico.backend.entity.Persona
import com.eclectico.backend.repository.PersonaRepository
import org.springframework.stereotype.Service

@Service
class PersonaService(private val personaRepository: PersonaRepository) {

    fun buscarPorEmail(email: String): Persona? = personaRepository.findByEmail(email)

    fun buscarPorTelefono(telefono: String): Persona? = personaRepository.findByTelefono(telefono)

    fun guardar(persona: Persona): Persona = personaRepository.save(persona)
}