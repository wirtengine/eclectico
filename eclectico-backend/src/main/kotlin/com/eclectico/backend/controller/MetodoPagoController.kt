package com.eclectico.backend.controller

import com.eclectico.backend.dto.MetodoPagoResponse
import com.eclectico.backend.entity.MetodoPago
import com.eclectico.backend.service.MetodoPagoService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/metodos-pago")
class MetodoPagoController(private val service: MetodoPagoService) {

    @GetMapping
    fun listar(): List<MetodoPagoResponse> = service.listar()

    @GetMapping("/{id}")
    fun obtener(@PathVariable id: Int): ResponseEntity<MetodoPagoResponse> =
        service.buscarPorId(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    fun crear(@RequestBody metodo: MetodoPago): MetodoPagoResponse = service.crear(metodo)

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    fun actualizar(@PathVariable id: Int, @RequestBody metodo: MetodoPago): ResponseEntity<MetodoPagoResponse> =
        service.actualizar(id, metodo)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    fun eliminar(@PathVariable id: Int): ResponseEntity<Void> =
        if (service.eliminar(id)) ResponseEntity.noContent().build() else ResponseEntity.notFound().build()
}