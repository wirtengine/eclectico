package com.eclectico.backend.controller

import com.eclectico.backend.dto.ProductoRequest
import com.eclectico.backend.dto.ProductoResponse
import com.eclectico.backend.service.ProductoService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/productos")
class ProductoController(private val productoService: ProductoService) {

    @GetMapping
    fun listarDisponibles(): List<ProductoResponse> = productoService.listarDisponibles()

    @GetMapping("/{codigo}")
    fun obtenerPorCodigo(@PathVariable codigo: String): ProductoResponse? =
        productoService.buscarPorCodigo(codigo)

    @PostMapping
    fun crearProducto(@Valid @RequestBody request: ProductoRequest): ProductoResponse =
        productoService.guardar(request)
}