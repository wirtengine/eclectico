package com.eclectico.backend.dto

data class LoginResponse(
    val token: String,
    val email: String,
    val role: String
)