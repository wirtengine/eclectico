package com.eclectico.backend.controller

import com.eclectico.backend.entity.Producto
import com.eclectico.backend.service.ProductoService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/productos")
class ProductoController(private val productoService: ProductoService) {

    @GetMapping
    fun listarDisponibles(): List<Producto> = productoService.listarDisponibles()

    @GetMapping("/{codigo}")
    fun obtenerPorCodigo(@PathVariable codigo: String): Producto? =
        productoService.buscarPorCodigo(codigo)

    @PostMapping
    fun crearProducto(@RequestBody producto: Producto): Producto =
        productoService.guardar(producto)
}