package com.eclectico.backend.controller

import com.eclectico.backend.dto.PersonaResponse
import com.eclectico.backend.dto.toResponse
import com.eclectico.backend.service.PersonaService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/personas")
class PersonaController(private val personaService: PersonaService) {

    @GetMapping("/email/{email}")
    fun obtenerPorEmail(@PathVariable email: String): PersonaResponse? {
        val persona = personaService.buscarPorEmail(email)
        return persona?.toResponse()
    }

    @GetMapping("/telefono/{telefono}")
    fun obtenerPorTelefono(@PathVariable telefono: String): PersonaResponse? =
        personaService.buscarPorTelefono(telefono)
}