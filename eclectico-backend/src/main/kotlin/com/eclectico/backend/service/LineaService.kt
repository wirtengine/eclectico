package com.eclectico.backend.service

import com.eclectico.backend.dto.LineaResponse
import com.eclectico.backend.dto.toResponse
import com.eclectico.backend.entity.Linea
import com.eclectico.backend.repository.LineaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LineaService(private val lineaRepository: LineaRepository) {

    fun listar(): List<LineaResponse> = lineaRepository.findAll().map { it.toResponse() }

    fun buscarPorId(id: Int): LineaResponse? = lineaRepository.findById(id).orElse(null)?.toResponse()

    @Transactional
    fun crear(linea: Linea): LineaResponse = lineaRepository.save(linea).toResponse()

    @Transactional
    fun actualizar(id: Int, linea: Linea): LineaResponse? {
        val existente = lineaRepository.findById(id).orElse(null) ?: return null
        existente.nombre = linea.nombre
        existente.descripcion = linea.descripcion
        return lineaRepository.save(existente).toResponse()
    }

    @Transactional
    fun eliminar(id: Int): Boolean {
        if (lineaRepository.existsById(id)) {
            lineaRepository.deleteById(id)
            return true
        }
        return false
    }
}