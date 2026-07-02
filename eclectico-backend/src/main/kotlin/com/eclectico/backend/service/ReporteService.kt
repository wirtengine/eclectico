package com.eclectico.backend.service

import com.eclectico.backend.dto.*
import com.eclectico.backend.repository.ReporteRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@Service
class ReporteService(private val reporteRepository: ReporteRepository) {

    fun inventarioDisponible(): List<InventarioDisponibleResponse> {
        return reporteRepository.inventarioDisponible().map { row ->
            InventarioDisponibleResponse(
                idProducto = row[0] as UUID,
                codigo = row[1] as String,
                descripcion = row[2] as String,
                linea = row[3] as String,
                talla = row[4] as String?,
                medidas = row[5] as String?,
                precioVenta = row[6] as BigDecimal,
                costo = row[7] as BigDecimal,
                margen = row[8] as BigDecimal,
                precioOfertaSugerido = row[9] as BigDecimal,
                imagenPrincipal = row[10] as String?,
                estadoNotas = row[11] as String?,
                fechaPublicacion = (row[12] as? java.sql.Date)?.toLocalDate()
            )
        }
    }

    fun ofertasRelampago(): List<OfertaRelampagoResponse> {
        return reporteRepository.ofertasRelampago().map { row ->
            OfertaRelampagoResponse(
                idProducto = row[0] as UUID,
                codigo = row[1] as String,
                descripcion = row[2] as String,
                linea = row[3] as String,
                talla = row[4] as String?,
                medidas = row[5] as String?,
                precioVenta = row[6] as BigDecimal,
                costo = row[7] as BigDecimal,
                margen = row[8] as BigDecimal,
                precioOfertaSugerido = row[9] as BigDecimal,
                descuentoPct = row[13] as BigDecimal,  // descuento_pct
                imagenPrincipal = row[10] as String?,
                estadoNotas = row[11] as String?,
                fechaPublicacion = (row[12] as? java.sql.Date)?.toLocalDate()
            )
        }
    }

    fun kpisSemanales(): List<KpiSemanalResponse> {
        return reporteRepository.kpisSemanales().map { row ->
            KpiSemanalResponse(
                semanaInicio = (row[0] as java.sql.Date).toLocalDate(),
                prendasVendidas = (row[1] as Number).toLong(),
                ingresos = row[2] as BigDecimal?,
                costos = row[3] as BigDecimal?,
                margenBruto = row[4] as BigDecimal?,
                utilidadNeta = row[5] as BigDecimal?
            )
        }
    }

    fun productosRecomendados(clienteId: UUID): List<ProductoRecomendadoResponse> {
        return reporteRepository.productosRecomendados(clienteId.toString()).map { row ->
            ProductoRecomendadoResponse(
                idProducto = row[0] as UUID,
                descripcion = row[1] as String,
                precio = row[2] as BigDecimal?,
                motivo = row[3] as String?
            )
        }
    }

    fun refrescarKpis() = reporteRepository.refrescarKpis()
}