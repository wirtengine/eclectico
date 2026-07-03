package com.eclectico.backend.service

import com.eclectico.backend.dto.ProductoRequest
import com.eclectico.backend.dto.ProductoResponse
import com.eclectico.backend.dto.toResponse
import com.eclectico.backend.entity.*
import com.eclectico.backend.repository.ProductoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

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

    fun listarVendidos(): List<ProductoResponse> =
        productoRepository.findByEstadoIdEstado(2).map { it.toResponse() }

    fun buscarPorId(id: UUID): ProductoResponse? =
        productoRepository.findById(id).orElse(null)?.toResponse()

    @Transactional
    fun actualizarProducto(id: UUID, request: ProductoRequest): ProductoResponse? {
        val producto = productoRepository.findById(id).orElse(null) ?: return null
        producto.descripcion = request.descripcion
        producto.linea = Linea(idLinea = request.idLinea, nombre = "")
        producto.costo = request.costo
        producto.precioVenta = request.precioVenta
        producto.talla = request.talla
        producto.medidas = request.medidas
        producto.color = request.color
        producto.marcaOriginal = request.marcaOriginal
        producto.estadoNotas = request.estadoNotas
        request.idProveedor?.let { producto.proveedor = Proveedor(idProveedor = it, nombre = "") }
        producto.imagenPrincipal = request.imagenPrincipal
        producto.imagenes = request.imagenes

        // Actualizar estado si se proporciona
        request.idEstado?.let { idEstado ->
            producto.estado = EstadoProducto(idEstado = idEstado, nombre = "")
        }

        return productoRepository.save(producto).toResponse()
    }

    fun buscarDisponibles(query: String): List<ProductoResponse> {
        val resultados = productoRepository
            .findByEstadoIdEstadoAndDescripcionContainingIgnoreCaseOrCodigoContaining(
                idEstado = 1,
                descripcion = query,
                codigo = query
            )
        return resultados.map { it.toResponse() }
    }
}