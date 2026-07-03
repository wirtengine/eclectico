package com.eclectico.backend.controller

import com.eclectico.backend.dto.PersonaRequest
import com.eclectico.backend.dto.PersonaResponse
import com.eclectico.backend.dto.SocioRequest
import com.eclectico.backend.dto.toResponse
import com.eclectico.backend.service.PersonaService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.UUID

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

    @GetMapping("/clientes")
    fun listarClientes(): List<PersonaResponse> = personaService.listarClientes()

    @GetMapping("/buscar")
    fun buscarClientes(@RequestParam q: String): List<PersonaResponse> =
        personaService.buscarClientes(q)

    @GetMapping("/{id}")
    fun obtenerPorId(@PathVariable id: UUID): ResponseEntity<PersonaResponse> =
        personaService.buscarPorId(id)?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @PostMapping("/clientes")
    fun crearCliente(@Valid @RequestBody request: PersonaRequest): PersonaResponse =
        personaService.crearCliente(request)

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('admin')")
    fun actualizarCliente(
        @PathVariable id: UUID,
        @Valid @RequestBody request: PersonaRequest
    ): ResponseEntity<PersonaResponse> =
        personaService.actualizarCliente(id, request)?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    // ---------- ENDPOINTS DE SOCIOS ----------
    @GetMapping("/socios")
    fun listarSocios(): List<PersonaResponse> = personaService.listarSocios()

    @PostMapping("/socios")
    @PreAuthorize("hasRole('admin')")
    fun crearSocio(@Valid @RequestBody request: SocioRequest): PersonaResponse =
        personaService.crearSocio(request)

    @PutMapping("/socios/{id}")
    @PreAuthorize("hasRole('admin')")
    fun actualizarSocio(
        @PathVariable id: UUID,
        @Valid @RequestBody request: SocioRequest
    ): ResponseEntity<PersonaResponse> =
        personaService.actualizarSocio(id, request)?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
}