package com.eclectico.backend.service

import com.eclectico.backend.dto.NotificacionResponse
import com.eclectico.backend.repository.NotificacionPendienteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class NotificacionService(private val notificacionRepository: NotificacionPendienteRepository) {

    fun listar(): List<NotificacionResponse> = notificacionRepository.findAll().map { not ->
        NotificacionResponse(
            id = not.id!!,
            cliente = not.cliente.nombre,
            tipo = not.tipo,
            mensaje = not.mensaje,
            creadoEn = not.creadoEn,
            enviado = not.enviado ?: false
        )
    }

    @Transactional
    fun marcarEnviada(id: UUID): Boolean {
        val not = notificacionRepository.findById(id).orElse(null) ?: return false
        not.enviado = true
        notificacionRepository.save(not)
        return true
    }
}