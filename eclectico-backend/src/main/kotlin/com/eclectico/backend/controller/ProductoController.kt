package com.eclectico.backend.controller

import com.eclectico.backend.dto.ProductoRequest
import com.eclectico.backend.dto.ProductoResponse
import com.eclectico.backend.service.ProductoService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/productos")
class ProductoController(private val productoService: ProductoService) {

    @GetMapping
    fun listarDisponibles(): List<ProductoResponse> = productoService.listarDisponibles()

    @GetMapping("/codigo/{codigo}")
    fun obtenerPorCodigo(@PathVariable codigo: String): ProductoResponse? =
        productoService.buscarPorCodigo(codigo)

    @PostMapping
    fun crearProducto(@Valid @RequestBody request: ProductoRequest): ProductoResponse =
        productoService.guardar(request)

    // ---------- NUEVO ENDPOINT DE BÚSQUEDA ----------
    @GetMapping("/buscar")
    fun buscarDisponibles(@RequestParam q: String): List<ProductoResponse> =
        productoService.buscarDisponibles(q)

    @GetMapping("/vendidos")
    fun listarVendidos(): List<ProductoResponse> = productoService.listarVendidos()

    @GetMapping("/{id}")
    fun obtenerPorId(@PathVariable id: UUID): ResponseEntity<ProductoResponse> =
        productoService.buscarPorId(id)?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('admin','marketing')")
    fun actualizarProducto(
        @PathVariable id: UUID,
        @Valid @RequestBody request: ProductoRequest
    ): ResponseEntity<ProductoResponse> =
        productoService.actualizarProducto(id, request)?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
}