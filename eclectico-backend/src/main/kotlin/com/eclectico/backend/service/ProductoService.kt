package com.eclectico.backend.service

import com.eclectico.backend.entity.Producto
import com.eclectico.backend.repository.ProductoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProductoService(private val productoRepository: ProductoRepository) {

    fun listarDisponibles(): List<Producto> =
        productoRepository.findByEstadoIdEstado(1) // 1 = Disponible

    fun buscarPorCodigo(codigo: String): Producto? =
        productoRepository.findByCodigo(codigo)

    fun guardar(producto: Producto): Producto =
        productoRepository.save(producto)
}