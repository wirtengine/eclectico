package com.eclectico.backend.controller

import com.eclectico.backend.dto.AuditLogResponse
import com.eclectico.backend.service.AuditLogService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auditoria")
@PreAuthorize("hasRole('admin')")
class AuditLogController(private val auditLogService: AuditLogService) {

    @GetMapping
    fun listar(
        @RequestParam(required = false) tabla: String?,
        @RequestParam(required = false) operacion: String?
    ): List<AuditLogResponse> = auditLogService.listar(tabla, operacion)
}