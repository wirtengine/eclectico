package com.eclectico.backend.repository

import com.eclectico.backend.entity.Venta
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface VentaRepository : JpaRepository<Venta, UUID>