package com.eclectico.backend.service

import com.eclectico.backend.dto.GastoRequest
import com.eclectico.backend.dto.GastoResponse
import com.eclectico.backend.entity.Gasto
import com.eclectico.backend.entity.Persona
import com.eclectico.backend.repository.GastoRepository
import com.eclectico.backend.repository.PersonaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.UUID

@Service
class GastoService(
    private val gastoRepository: GastoRepository,
    private val personaRepository: PersonaRepository
) {
    fun listar(): List<GastoResponse> = gastoRepository.findAll().map { it.toResponse() }

    fun buscarPorId(id: UUID): GastoResponse? = gastoRepository.findById(id).orElse(null)?.toResponse()

    @Transactional
    fun crear(request: GastoRequest): GastoResponse {
        val socio = request.idSocio?.let { personaRepository.findById(UUID.fromString(it)).orElse(null) }
        val gasto = Gasto(
            concepto = request.concepto,
            monto = request.monto,
            fecha = request.fecha?.let { LocalDate.parse(it) } ?: LocalDate.now(),
            socio = socio,
            comprobanteUrl = request.comprobanteUrl
        )
        return gastoRepository.save(gasto).toResponse()
    }

    @Transactional
    fun actualizar(id: UUID, request: GastoRequest): GastoResponse? {
        val gasto = gastoRepository.findById(id).orElse(null) ?: return null
        gasto.concepto = request.concepto
        gasto.monto = request.monto
        request.fecha?.let { gasto.fecha = LocalDate.parse(it) }
        request.idSocio?.let { socioId ->
            gasto.socio = personaRepository.findById(UUID.fromString(socioId)).orElse(null)
        }
        gasto.comprobanteUrl = request.comprobanteUrl
        return gastoRepository.save(gasto).toResponse()
    }

    @Transactional
    fun eliminar(id: UUID): Boolean {
        if (gastoRepository.existsById(id)) {
            gastoRepository.deleteById(id)
            return true
        }
        return false
    }

    private fun Gasto.toResponse() = GastoResponse(
        idGasto = this.idGasto!!,
        fecha = this.fecha!!,
        concepto = this.concepto,
        monto = this.monto,
        socio = this.socio?.nombre,
        comprobanteUrl = this.comprobanteUrl
    )
}