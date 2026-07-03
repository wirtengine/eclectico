package com.eclectico.backend.repository

import com.eclectico.backend.entity.DetalleVenta
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface DetalleVentaRepository : JpaRepository<DetalleVenta, UUID> {
    fun findAllByVentaIdVenta(idVenta: UUID): List<DetalleVenta>
}