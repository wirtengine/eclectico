package com.eclectico.backend.repository

import com.eclectico.backend.entity.DetalleVenta
import org.springframework.data.jpa.repository.JpaRepository

interface DetalleVentaRepository : JpaRepository<DetalleVenta, Int>