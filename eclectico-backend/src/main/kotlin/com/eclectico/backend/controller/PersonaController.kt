package com.eclectico.backend.controller

import com.eclectico.backend.entity.Persona
import com.eclectico.backend.service.PersonaService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/personas")
class PersonaController(private val personaService: PersonaService) {

    @GetMapping("/email/{email}")
    fun obtenerPorEmail(@PathVariable email: String): Persona? =
        personaService.buscarPorEmail(email)

    @GetMapping("/telefono/{telefono}")
    fun obtenerPorTelefono(@PathVariable telefono: String): Persona? =
        personaService.buscarPorTelefono(telefono)

    @PostMapping
    fun crearPersona(@RequestBody persona: Persona): Persona =
        personaService.guardar(persona)
}