package com.eclectico.backend.service

import com.eclectico.backend.dto.MetodoPagoResponse
import com.eclectico.backend.dto.toResponse
import com.eclectico.backend.entity.MetodoPago
import com.eclectico.backend.repository.MetodoPagoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MetodoPagoService(private val repository: MetodoPagoRepository) {

    fun listar(): List<MetodoPagoResponse> = repository.findAll().map { it.toResponse() }

    fun buscarPorId(id: Int): MetodoPagoResponse? =
        repository.findById(id).orElse(null)?.toResponse()

    @Transactional
    fun crear(metodo: MetodoPago): MetodoPagoResponse = repository.save(metodo).toResponse()

    @Transactional
    fun actualizar(id: Int, metodo: MetodoPago): MetodoPagoResponse? {
        val existente = repository.findById(id).orElse(null) ?: return null
        existente.nombre = metodo.nombre
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