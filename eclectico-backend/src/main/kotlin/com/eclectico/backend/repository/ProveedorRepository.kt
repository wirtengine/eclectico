package com.eclectico.backend.repository

import com.eclectico.backend.entity.Proveedor
import org.springframework.data.jpa.repository.JpaRepository

interface ProveedorRepository : JpaRepository<Proveedor, Int>