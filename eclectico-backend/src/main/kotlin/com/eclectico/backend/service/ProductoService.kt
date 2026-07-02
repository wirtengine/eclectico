package com.eclectico.backend.service

import com.eclectico.backend.dto.ProductoRequest
import com.eclectico.backend.dto.ProductoResponse
import com.eclectico.backend.dto.toResponse
import com.eclectico.backend.entity.*
import com.eclectico.backend.repository.ProductoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductoService(private val productoRepository: ProductoRepository) {

    fun listarDisponibles(): List<ProductoResponse> =
        productoRepository.findByEstadoIdEstado(1).map { it.toResponse() }

    fun buscarPorCodigo(codigo: String): ProductoResponse? =
        productoRepository.findByCodigo(codigo)?.toResponse()

    @Transactional
    fun guardar(request: ProductoRequest): ProductoResponse {
        val linea = Linea(idLinea = request.idLinea, nombre = "")
        val estado = EstadoProducto(idEstado = 1, nombre = "")
        val proveedor = request.idProveedor?.let { Proveedor(idProveedor = it, nombre = "") }

        val producto = Producto(
            descripcion = request.descripcion,
            linea = linea,
            costo = request.costo,
            precioVenta = request.precioVenta,
            talla = request.talla,
            medidas = request.medidas,
            color = request.color,
            marcaOriginal = request.marcaOriginal,
            estadoNotas = request.estadoNotas,
            estado = estado,
            proveedor = proveedor,
            imagenPrincipal = request.imagenPrincipal,
            imagenes = request.imagenes
        )
        return productoRepository.save(producto).toResponse()
    }
}