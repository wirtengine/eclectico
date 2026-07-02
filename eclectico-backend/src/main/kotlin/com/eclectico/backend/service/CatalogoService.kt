package com.eclectico.backend.service

import com.eclectico.backend.dto.*
import com.eclectico.backend.repository.*
import org.springframework.stereotype.Service

@Service
class CatalogoService(
    private val lineaRepository: LineaRepository,
    private val estadoProductoRepository: EstadoProductoRepository,
    private val proveedorRepository: ProveedorRepository,
    private val metodoPagoRepository: MetodoPagoRepository
) {
    fun listarLineas(): List<LineaResponse> = lineaRepository.findAll().map { it.toResponse() }
    fun listarEstados(): List<EstadoProductoResponse> = estadoProductoRepository.findAll().map { it.toResponse() }
    fun listarProveedores(): List<ProveedorResponse> = proveedorRepository.findAll().map { it.toResponse() }
    fun listarMetodosPago(): List<MetodoPagoResponse> = metodoPagoRepository.findAll().map { it.toResponse() }
}