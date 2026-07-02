package com.eclectico.backend.service

import com.eclectico.backend.entity.Venta
import com.eclectico.backend.repository.VentaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class VentaService(private val ventaRepository: VentaRepository) {

    fun guardar(venta: Venta): Venta = ventaRepository.save(venta)
}