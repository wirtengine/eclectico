package com.eclectico.backend.controller

import com.eclectico.backend.dto.NotificacionResponse
import com.eclectico.backend.service.NotificacionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/notificaciones")
class NotificacionController(private val notificacionService: NotificacionService) {

    @GetMapping
    fun listar(): List<NotificacionResponse> = notificacionService.listar()

    @PutMapping("/{id}/enviar")
    fun marcarEnviada(@PathVariable id: UUID): ResponseEntity<Void> =
        if (notificacionService.marcarEnviada(id)) ResponseEntity.ok().build() else ResponseEntity.notFound().build()
}