package com.eclectico.backend.repository

import com.eclectico.backend.entity.EstadoProducto
import org.springframework.data.jpa.repository.JpaRepository

interface EstadoProductoRepository : JpaRepository<EstadoProducto, Int>