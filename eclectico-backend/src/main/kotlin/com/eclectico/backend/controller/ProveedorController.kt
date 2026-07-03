package com.eclectico.backend.controller

import com.eclectico.backend.dto.ProveedorResponse
import com.eclectico.backend.entity.Proveedor
import com.eclectico.backend.service.ProveedorService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/proveedores")
class ProveedorController(private val proveedorService: ProveedorService) {

    @GetMapping
    fun listar(): List<ProveedorResponse> = proveedorService.listar()

    @GetMapping("/{id}")
    fun obtener(@PathVariable id: Int): ResponseEntity<ProveedorResponse> =
        proveedorService.buscarPorId(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    fun crear(@RequestBody proveedor: Proveedor): ProveedorResponse = proveedorService.crear(proveedor)

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    fun actualizar(@PathVariable id: Int, @RequestBody proveedor: Proveedor): ResponseEntity<ProveedorResponse> =
        proveedorService.actualizar(id, proveedor)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    fun eliminar(@PathVariable id: Int): ResponseEntity<Void> =
        if (proveedorService.eliminar(id)) ResponseEntity.noContent().build() else ResponseEntity.notFound().build()
}