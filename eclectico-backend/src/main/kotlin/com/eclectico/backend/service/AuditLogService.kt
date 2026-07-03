package com.eclectico.backend.service

import com.eclectico.backend.dto.AuditLogResponse
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Service

@Service
class AuditLogService(
    @PersistenceContext private val entityManager: EntityManager
) {
    fun listar(tabla: String?, operacion: String?): List<AuditLogResponse> {
        var sql = "SELECT * FROM audit_log WHERE 1=1"
        var paramIndex = 1

        if (!tabla.isNullOrBlank()) {
            sql += " AND tabla_afectada = ?$paramIndex"
            paramIndex++
        }
        if (!operacion.isNullOrBlank()) {
            sql += " AND operacion = ?$paramIndex"
            paramIndex++
        }
        sql += " ORDER BY fecha DESC LIMIT 1000"

        val query = entityManager.createNativeQuery(sql)
        paramIndex = 1
        if (!tabla.isNullOrBlank()) {
            query.setParameter(paramIndex, tabla)
            paramIndex++
        }
        if (!operacion.isNullOrBlank()) {
            query.setParameter(paramIndex, operacion)
            paramIndex++
        }

        @Suppress("UNCHECKED_CAST")
        val results = query.resultList as List<Array<Any>>
        return results.map { row ->
            AuditLogResponse(
                idAudit = (row[0] as Number).toLong(),
                tablaAfectada = row[1] as String,
                operacion = row[2] as String,
                idRegistro = row[3] as String,
                usuario = row[4] as String,
                fecha = row[5]?.let { (it as java.sql.Timestamp).toLocalDateTime() },
                datosAnteriores = row[6],
                datosNuevos = row[7]
            )
        }
    }
}