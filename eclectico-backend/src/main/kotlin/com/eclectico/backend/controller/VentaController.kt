package com.eclectico.backend.controller

import com.eclectico.backend.dto.VentaRequest
import com.eclectico.backend.dto.VentaResponse
import com.eclectico.backend.service.VentaService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/ventas")
class VentaController(private val ventaService: VentaService) {

    @PostMapping
    fun crearVenta(@Valid @RequestBody request: VentaRequest): ResponseEntity<VentaResponse> {
        val respuesta = ventaService.crearVenta(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta)
    }

    // ---------- NUEVOS ENDPOINTS ----------
    @GetMapping
    fun listarTodas(): List<VentaResponse> = ventaService.listarTodas()

    @GetMapping("/{id}")
    fun obtener(@PathVariable id: UUID): ResponseEntity<VentaResponse> =
        ventaService.buscarPorId(id)?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @PutMapping("/{id}/estado-entrega")
    @PreAuthorize("hasAnyRole('admin','operaciones')")
    fun actualizarEstadoEntrega(
        @PathVariable id: UUID,
        @RequestBody estado: Map<String, String>
    ): ResponseEntity<VentaResponse> {
        val nuevoEstado = estado["estado"] ?: return ResponseEntity.badRequest().build()
        return ventaService.actualizarEstadoEntrega(id, nuevoEstado)?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }
}