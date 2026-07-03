package com.eclectico.backend.service

import com.eclectico.backend.dto.ConfiguracionResponse
import com.eclectico.backend.repository.ConfiguracionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ConfiguracionService(private val configuracionRepository: ConfiguracionRepository) {

    fun listar(): List<ConfiguracionResponse> = configuracionRepository.findAll().map {
        ConfiguracionResponse(it.clave, it.valor)
    }

    @Transactional
    fun actualizar(clave: String, valor: String): ConfiguracionResponse? {
        val config = configuracionRepository.findById(clave).orElse(null) ?: return null
        config.valor = valor
        configuracionRepository.save(config)
        return ConfiguracionResponse(config.clave, config.valor)
    }
}