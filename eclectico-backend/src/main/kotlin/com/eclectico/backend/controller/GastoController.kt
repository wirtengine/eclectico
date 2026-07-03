package com.eclectico.backend.controller

import com.eclectico.backend.dto.GastoRequest
import com.eclectico.backend.dto.GastoResponse
import com.eclectico.backend.service.GastoService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/gastos")
class GastoController(private val gastoService: GastoService) {

    @GetMapping
    fun listar(): List<GastoResponse> = gastoService.listar()

    @GetMapping("/{id}")
    fun obtener(@PathVariable id: UUID): ResponseEntity<GastoResponse> =
        gastoService.buscarPorId(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @PostMapping
    @PreAuthorize("hasAnyRole('admin','operaciones')")
    fun crear(@Valid @RequestBody request: GastoRequest): GastoResponse = gastoService.crear(request)

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('admin','operaciones')")
    fun actualizar(@PathVariable id: UUID, @Valid @RequestBody request: GastoRequest): ResponseEntity<GastoResponse> =
        gastoService.actualizar(id, request)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    fun eliminar(@PathVariable id: UUID): ResponseEntity<Void> =
        if (gastoService.eliminar(id)) ResponseEntity.noContent().build() else ResponseEntity.notFound().build()
}