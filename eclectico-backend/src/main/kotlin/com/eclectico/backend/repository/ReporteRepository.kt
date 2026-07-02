package com.eclectico.backend.repository

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository

@Repository
class ReporteRepository(
    @PersistenceContext private val entityManager: EntityManager
) {
    fun inventarioDisponible(): List<Array<Any>> {
        val query = entityManager.createNativeQuery("SELECT * FROM v_inventario_disponible")
        return query.resultList.map { it as Array<Any> }
    }

    fun ofertasRelampago(): List<Array<Any>> {
        val query = entityManager.createNativeQuery("SELECT * FROM v_ofertas_relampago")
        return query.resultList.map { it as Array<Any> }
    }

    fun kpisSemanales(): List<Array<Any>> {
        val query = entityManager.createNativeQuery("SELECT * FROM mv_kpi_semanal")
        return query.resultList.map { it as Array<Any> }
    }

    fun productosRecomendados(clienteId: String): List<Array<Any>> {
        val query = entityManager.createNativeQuery("SELECT * FROM productos_recomendados(?1)")
        query.setParameter(1, clienteId)
        return query.resultList.map { it as Array<Any> }
    }

    fun refrescarKpis() {
        entityManager.createNativeQuery("REFRESH MATERIALIZED VIEW CONCURRENTLY mv_kpi_semanal").executeUpdate()
    }
}