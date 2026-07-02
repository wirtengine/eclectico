package com.eclectico.backend.repository

import com.eclectico.backend.entity.NotificacionPendiente
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface NotificacionPendienteRepository : JpaRepository<NotificacionPendiente, UUID>