package com.eclectico.backend.controller

import com.eclectico.backend.dto.LineaResponse
import com.eclectico.backend.entity.Linea
import com.eclectico.backend.service.LineaService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/lineas")
class LineaController(private val lineaService: LineaService) {

    @GetMapping
    fun listar(): List<LineaResponse> = lineaService.listar()

    @GetMapping("/{id}")
    fun obtener(@PathVariable id: Int): ResponseEntity<LineaResponse> =
        lineaService.buscarPorId(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    fun crear(@RequestBody linea: Linea): LineaResponse = lineaService.crear(linea)

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    fun actualizar(@PathVariable id: Int, @RequestBody linea: Linea): ResponseEntity<LineaResponse> =
        lineaService.actualizar(id, linea)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    fun eliminar(@PathVariable id: Int): ResponseEntity<Void> =
        if (lineaService.eliminar(id)) ResponseEntity.noContent().build() else ResponseEntity.notFound().build()
}