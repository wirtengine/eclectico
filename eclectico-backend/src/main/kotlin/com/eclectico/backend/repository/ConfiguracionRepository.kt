package com.eclectico.backend.repository

import com.eclectico.backend.entity.Configuracion
import org.springframework.data.jpa.repository.JpaRepository

interface ConfiguracionRepository : JpaRepository<Configuracion, String>