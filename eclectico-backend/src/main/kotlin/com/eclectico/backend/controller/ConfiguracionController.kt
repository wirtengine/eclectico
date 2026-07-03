package com.eclectico.backend.controller

import com.eclectico.backend.dto.ConfiguracionResponse
import com.eclectico.backend.dto.ConfiguracionUpdateRequest
import com.eclectico.backend.service.ConfiguracionService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/configuracion")
class ConfiguracionController(private val configuracionService: ConfiguracionService) {

    @GetMapping
    fun listar(): List<ConfiguracionResponse> = configuracionService.listar()

    @PutMapping("/{clave}")
    @PreAuthorize("hasRole('admin')")
    fun actualizar(@PathVariable clave: String, @RequestBody request: ConfiguracionUpdateRequest): ResponseEntity<ConfiguracionResponse> =
        configuracionService.actualizar(clave, request.valor)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()
}