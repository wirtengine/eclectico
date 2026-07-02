package com.eclectico.backend.repository

import com.eclectico.backend.entity.MetodoPago
import org.springframework.data.jpa.repository.JpaRepository

interface MetodoPagoRepository : JpaRepository<MetodoPago, Int>