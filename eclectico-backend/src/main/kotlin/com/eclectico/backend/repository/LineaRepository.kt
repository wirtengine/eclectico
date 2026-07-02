package com.eclectico.backend.repository

import com.eclectico.backend.entity.Linea
import org.springframework.data.jpa.repository.JpaRepository

interface LineaRepository : JpaRepository<Linea, Int>