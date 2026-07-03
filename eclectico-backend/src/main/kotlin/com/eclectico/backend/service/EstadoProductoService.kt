package com.eclectico.backend.service

import com.eclectico.backend.dto.EstadoProductoResponse
import com.eclectico.backend.dto.toResponse
import com.eclectico.backend.entity.EstadoProducto
import com.eclectico.backend.repository.EstadoProductoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EstadoProductoService(private val repository: EstadoProductoRepository) {

    fun listar(): List<EstadoProductoResponse> = repository.findAll().map { it.toResponse() }

    fun buscarPorId(id: Int): EstadoProductoResponse? =
        repository.findById(id).orElse(null)?.toResponse()

    @Transactional
    fun crear(estado: EstadoProducto): EstadoProductoResponse = repository.save(estado).toResponse()

    @Transactional
    fun actualizar(id: Int, estado: EstadoProducto): EstadoProductoResponse? {
        val existente = repository.findById(id).orElse(null) ?: return null
        existente.nombre = estado.nombre
        return repository.save(existente).toResponse()
    }

    @Transactional
    fun eliminar(id: Int): Boolean {
        if (repository.existsById(id)) {
            repository.deleteById(id)
            return true
        }
        return false
    }
}