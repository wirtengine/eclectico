package com.eclectico.backend.controller

import com.eclectico.backend.dto.RolUsuarioResponse
import com.eclectico.backend.entity.RolUsuario
import com.eclectico.backend.service.RolUsuarioService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/roles")
class RolUsuarioController(private val rolUsuarioService: RolUsuarioService) {

    @GetMapping
    fun listar(): List<RolUsuarioResponse> = rolUsuarioService.listar()

    @GetMapping("/{id}")
    fun obtener(@PathVariable id: Int): ResponseEntity<RolUsuarioResponse> =
        rolUsuarioService.buscarPorId(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    fun crear(@RequestBody rol: RolUsuario): RolUsuarioResponse = rolUsuarioService.crear(rol)

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    fun actualizar(@PathVariable id: Int, @RequestBody rol: RolUsuario): ResponseEntity<RolUsuarioResponse> =
        rolUsuarioService.actualizar(id, rol)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    fun eliminar(@PathVariable id: Int): ResponseEntity<Void> =
        if (rolUsuarioService.eliminar(id)) ResponseEntity.noContent().build() else ResponseEntity.notFound().build()
}