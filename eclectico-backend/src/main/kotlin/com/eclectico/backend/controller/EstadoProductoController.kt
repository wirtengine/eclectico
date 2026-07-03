package com.eclectico.backend.controller

import com.eclectico.backend.dto.EstadoProductoResponse
import com.eclectico.backend.entity.EstadoProducto
import com.eclectico.backend.service.EstadoProductoService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/estados-producto")
class EstadoProductoController(private val service: EstadoProductoService) {

    @GetMapping
    fun listar(): List<EstadoProductoResponse> = service.listar()

    @GetMapping("/{id}")
    fun obtener(@PathVariable id: Int): ResponseEntity<EstadoProductoResponse> =
        service.buscarPorId(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    fun crear(@RequestBody estado: EstadoProducto): EstadoProductoResponse = service.crear(estado)

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    fun actualizar(@PathVariable id: Int, @RequestBody estado: EstadoProducto): ResponseEntity<EstadoProductoResponse> =
        service.actualizar(id, estado)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    fun eliminar(@PathVariable id: Int): ResponseEntity<Void> =
        if (service.eliminar(id)) ResponseEntity.noContent().build() else ResponseEntity.notFound().build()
}