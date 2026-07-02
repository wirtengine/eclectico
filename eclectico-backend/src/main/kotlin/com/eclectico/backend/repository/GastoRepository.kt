package com.eclectico.backend.repository

import com.eclectico.backend.entity.Gasto
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface GastoRepository : JpaRepository<Gasto, UUID>