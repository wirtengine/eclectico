package com.eclectico.backend.service

import com.eclectico.backend.dto.ProveedorResponse
import com.eclectico.backend.dto.toResponse
import com.eclectico.backend.entity.Proveedor
import com.eclectico.backend.repository.ProveedorRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProveedorService(private val proveedorRepository: ProveedorRepository) {

    fun listar(): List<ProveedorResponse> = proveedorRepository.findAll().map { it.toResponse() }

    fun buscarPorId(id: Int): ProveedorResponse? = proveedorRepository.findById(id).orElse(null)?.toResponse()

    @Transactional
    fun crear(proveedor: Proveedor): ProveedorResponse = proveedorRepository.save(proveedor).toResponse()

    @Transactional
    fun actualizar(id: Int, proveedor: Proveedor): ProveedorResponse? {
        val existente = proveedorRepository.findById(id).orElse(null) ?: return null
        existente.nombre = proveedor.nombre
        existente.telefono = proveedor.telefono
        existente.ubicacion = proveedor.ubicacion
        existente.fechaUltimaVisita = proveedor.fechaUltimaVisita
        existente.calificacion = proveedor.calificacion
        return proveedorRepository.save(existente).toResponse()
    }

    @Transactional
    fun eliminar(id: Int): Boolean {
        if (proveedorRepository.existsById(id)) {
            proveedorRepository.deleteById(id)
            return true
        }
        return false
    }
}