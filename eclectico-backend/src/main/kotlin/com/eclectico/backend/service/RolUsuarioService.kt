package com.eclectico.backend.service

import com.eclectico.backend.dto.RolUsuarioResponse
import com.eclectico.backend.dto.toResponse
import com.eclectico.backend.entity.RolUsuario
import com.eclectico.backend.repository.RolUsuarioRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RolUsuarioService(private val rolUsuarioRepository: RolUsuarioRepository) {

    fun listar(): List<RolUsuarioResponse> = rolUsuarioRepository.findAll().map { it.toResponse() }

    fun buscarPorId(id: Int): RolUsuarioResponse? = rolUsuarioRepository.findById(id).orElse(null)?.toResponse()

    @Transactional
    fun crear(rol: RolUsuario): RolUsuarioResponse = rolUsuarioRepository.save(rol).toResponse()

    @Transactional
    fun actualizar(id: Int, rol: RolUsuario): RolUsuarioResponse? {
        val existente = rolUsuarioRepository.findById(id).orElse(null) ?: return null
        existente.nombre = rol.nombre
        return rolUsuarioRepository.save(existente).toResponse()
    }

    @Transactional
    fun eliminar(id: Int): Boolean {
        if (rolUsuarioRepository.existsById(id)) {
            rolUsuarioRepository.deleteById(id)
            return true
        }
        return false
    }
}