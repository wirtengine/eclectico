package com.eclectico.backend.controller

import com.eclectico.backend.dto.VentaRequest
import com.eclectico.backend.dto.VentaResponse
import com.eclectico.backend.service.VentaService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/ventas")
class VentaController(private val ventaService: VentaService) {

    @PostMapping
    fun crearVenta(@Valid @RequestBody request: VentaRequest): ResponseEntity<VentaResponse> {
        val respuesta = ventaService.crearVenta(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta)
    }
}