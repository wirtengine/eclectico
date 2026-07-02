package com.eclectico.backend.controller

import com.eclectico.backend.service.ReporteService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/reportes")
@Tag(name = "Reportes", description = "Endpoints de reportes y KPIs")
class ReporteController(private val reporteService: ReporteService) {

    @GetMapping("/inventario-disponible")
    @Operation(summary = "Obtener inventario disponible con precio sugerido")
    fun inventarioDisponible() = reporteService.inventarioDisponible()

    @GetMapping("/ofertas-relampago")
    @Operation(summary = "Ofertas relámpago (productos con descuento automático)")
    fun ofertasRelampago() = reporteService.ofertasRelampago()

    @GetMapping("/kpis-semanales")
    @Operation(summary = "KPIs semanales (ventas, costos, utilidad)")
    fun kpisSemanales() = reporteService.kpisSemanales()

    @PostMapping("/refrescar-kpis")
    @Operation(summary = "Refrescar la vista materializada de KPIs")
    fun refrescarKpis(): ResponseEntity<String> {
        reporteService.refrescarKpis()
        return ResponseEntity.ok("KPIs actualizados")
    }

    @GetMapping("/productos-recomendados/{clienteId}")
    @Operation(summary = "Productos recomendados para un cliente")
    fun productosRecomendados(@PathVariable clienteId: UUID) =
        reporteService.productosRecomendados(clienteId)
}