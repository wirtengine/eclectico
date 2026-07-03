package com.eclectico.backend.service

import com.eclectico.backend.dto.PersonaRequest
import com.eclectico.backend.dto.PersonaResponse
import com.eclectico.backend.dto.SocioRequest
import com.eclectico.backend.dto.toResponse
import com.eclectico.backend.entity.Persona
import com.eclectico.backend.entity.RolUsuario
import com.eclectico.backend.repository.PersonaRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class PersonaService(
    private val personaRepository: PersonaRepository,
    private val passwordEncoder: PasswordEncoder   // ← agregado
) {

    fun buscarPorEmail(email: String): Persona? = personaRepository.findByEmail(email)

    fun buscarPorTelefono(telefono: String): PersonaResponse? {
        val persona = personaRepository.findByTelefono(telefono)
        return persona?.toResponse()
    }

    fun guardar(persona: Persona): PersonaResponse = personaRepository.save(persona).toResponse()

    fun listarClientes(): List<PersonaResponse> =
        personaRepository.findAllByTipo('C').map { it.toResponse() }

    fun buscarPorId(id: UUID): PersonaResponse? =
        personaRepository.findById(id).orElse(null)?.toResponse()

    @Transactional
    fun crearCliente(request: PersonaRequest): PersonaResponse {
        val persona = Persona(
            nombre = request.nombre,
            telefono = request.telefono,
            email = request.email,
            direccion = request.direccion,
            tipo = 'C'
        )
        return personaRepository.save(persona).toResponse()
    }

    @Transactional
    fun actualizarCliente(id: UUID, request: PersonaRequest): PersonaResponse? {
        val persona = personaRepository.findById(id).orElse(null) ?: return null
        if (persona.tipo != 'C') return null
        persona.nombre = request.nombre
        persona.telefono = request.telefono
        persona.email = request.email
        persona.direccion = request.direccion
        return personaRepository.save(persona).toResponse()
    }

    fun buscarClientes(query: String): List<PersonaResponse> {
        val resultados = personaRepository.findByTipoAndNombreContainingIgnoreCaseOrTelefonoContaining(
            tipo = 'C',
            nombre = query,
            telefono = query
        )
        return resultados.map { it.toResponse() }
    }

    // ---------- MÉTODOS PARA SOCIOS ----------
    fun listarSocios(): List<PersonaResponse> =
        personaRepository.findAllByTipo('S').map { it.toResponse() }

    @Transactional
    fun crearSocio(request: SocioRequest): PersonaResponse {
        val persona = Persona(
            nombre = request.nombre,
            email = request.email,
            telefono = request.telefono,
            tipo = 'S',
            rol = request.idRol?.let { RolUsuario(idRol = it, nombre = "") }
        )
        if (!request.password.isNullOrBlank()) {
            persona.passwordHash = passwordEncoder.encode(request.password)
        }
        return personaRepository.save(persona).toResponse()
    }

    @Transactional
    fun actualizarSocio(id: UUID, request: SocioRequest): PersonaResponse? {
        val persona = personaRepository.findById(id).orElse(null) ?: return null
        if (persona.tipo != 'S') return null
        persona.nombre = request.nombre
        persona.email = request.email
        persona.telefono = request.telefono
        request.idRol?.let { persona.rol = RolUsuario(idRol = it, nombre = "") }
        if (!request.password.isNullOrBlank()) {
            persona.passwordHash = passwordEncoder.encode(request.password)
        }
        return personaRepository.save(persona).toResponse()
    }
}