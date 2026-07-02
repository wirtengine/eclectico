package com.eclectico.backend.controller

import com.eclectico.backend.dto.LoginRequest
import com.eclectico.backend.dto.LoginResponse
import com.eclectico.backend.security.JwtUtil
import com.eclectico.backend.service.PersonaService
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val personaService: PersonaService,
    private val jwtUtil: JwtUtil,
    private val passwordEncoder: PasswordEncoder
) {
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        val socio = personaService.buscarPorEmail(request.email)
            ?: throw RuntimeException("Credenciales inválidas")

        if (socio.tipo != 'S') throw RuntimeException("No es un socio")

        if (!passwordEncoder.matches(request.password, socio.passwordHash))
            throw RuntimeException("Credenciales inválidas")

        val role = socio.rol?.nombre ?: "admin"
        val token = jwtUtil.generateToken(socio.email!!, role)

        return ResponseEntity.ok(LoginResponse(token, socio.email!!, role))
    }
}