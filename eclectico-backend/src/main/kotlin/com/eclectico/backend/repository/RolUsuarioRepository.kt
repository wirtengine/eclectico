package com.eclectico.backend.repository

import com.eclectico.backend.entity.RolUsuario
import org.springframework.data.jpa.repository.JpaRepository

interface RolUsuarioRepository : JpaRepository<RolUsuario, Int>