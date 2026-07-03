package com.eclectico.backend.service

import com.eclectico.backend.dto.VentaRequest
import com.eclectico.backend.dto.VentaResponse
import com.eclectico.backend.dto.DetalleVentaResponse
import com.eclectico.backend.entity.*
import com.eclectico.backend.exception.VentaException
import com.eclectico.backend.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.UUID

@Service
class VentaService(
    private val ventaRepository: VentaRepository,
    private val productoRepository: ProductoRepository,
    private val personaRepository: PersonaRepository,
    private val metodoPagoRepository: MetodoPagoRepository,
    private val detalleVentaRepository: DetalleVentaRepository
) {

    @Transactional
    fun crearVenta(request: VentaRequest): VentaResponse {
        val cliente = personaRepository.findById(request.idCliente)
            .orElseThrow { VentaException("Cliente no encontrado") }
        if (cliente.tipo != 'C') throw VentaException("La persona no es un cliente")

        val vendedor = request.idVendedor?.let {
            personaRepository.findById(it)
                .orElseThrow { VentaException("Vendedor no encontrado") }
                .also { if (it.tipo != 'S') throw VentaException("El vendedor no es un socio") }
        }

        val metodoPago = metodoPagoRepository.findById(request.idMetodoPago)
            .orElseThrow { VentaException("Método de pago no válido") }

        val productos = mutableListOf<Producto>()
        var montoTotal = BigDecimal.ZERO
        var costoTotal = BigDecimal.ZERO

        for (detalleReq in request.detalles) {
            val producto = productoRepository.findById(detalleReq.idProducto)
                .orElseThrow { VentaException("Producto no encontrado: ${detalleReq.idProducto}") }
            if (producto.estado?.idEstado != 1) {
                throw VentaException("Producto no disponible: ${producto.codigo}")
            }
            montoTotal += detalleReq.precioVentaReal
            costoTotal += producto.costo
            productos.add(producto)
        }

        val venta = Venta(
            cliente = cliente,
            vendedor = vendedor,
            metodoPago = metodoPago,
            montoTotal = montoTotal,
            costoTotal = costoTotal,
            estadoEntrega = request.estadoEntrega,
            notas = request.notas
        )
        val ventaGuardada = ventaRepository.save(venta)

        val detalles = request.detalles.mapIndexed { index, detalleReq ->
            DetalleVenta(
                venta = ventaGuardada,
                producto = productos[index],
                precioVentaReal = detalleReq.precioVentaReal,
                descuentoAplicado = detalleReq.descuentoAplicado
            )
        }
        detalleVentaRepository.saveAll(detalles)

        return construirRespuesta(ventaGuardada, detalles)
    }

    // ---------- NUEVOS MÉTODOS ----------
    fun listarTodas(): List<VentaResponse> = ventaRepository.findAll().map { venta ->
        val detalles = detalleVentaRepository.findAllByVentaIdVenta(venta.idVenta!!)
        construirRespuesta(venta, detalles)
    }

    fun buscarPorId(id: UUID): VentaResponse? {
        val venta = ventaRepository.findById(id).orElse(null) ?: return null
        val detalles = detalleVentaRepository.findAllByVentaIdVenta(venta.idVenta!!)
        return construirRespuesta(venta, detalles)
    }

    @Transactional
    fun actualizarEstadoEntrega(id: UUID, nuevoEstado: String): VentaResponse? {
        val venta = ventaRepository.findById(id).orElse(null) ?: return null
        venta.estadoEntrega = nuevoEstado   // ahora funciona porque estadoEntrega es var
        val ventaActualizada = ventaRepository.save(venta)
        val detalles = detalleVentaRepository.findAllByVentaIdVenta(ventaActualizada.idVenta!!)
        return construirRespuesta(ventaActualizada, detalles)
    }

    // ---------- MÉTODO PRIVADO REUTILIZABLE ----------
    private fun construirRespuesta(venta: Venta, detalles: List<DetalleVenta>): VentaResponse {
        return VentaResponse(
            idVenta = venta.idVenta!!,
            fechaVenta = venta.fechaVenta!!,
            idCliente = venta.cliente.idPersona!!,
            nombreCliente = venta.cliente.nombre,
            montoTotal = venta.montoTotal,
            costoTotal = venta.costoTotal,
            margenBruto = venta.margenBruto ?: BigDecimal.ZERO,
            metodoPago = venta.metodoPago?.nombre,
            estadoEntrega = venta.estadoEntrega!!,
            vendedor = venta.vendedor?.nombre,
            notas = venta.notas,
            detalles = detalles.map { detalle ->
                DetalleVentaResponse(
                    idProducto = detalle.producto.idProducto!!,
                    codigoProducto = detalle.producto.codigo!!,
                    descripcionProducto = detalle.producto.descripcion,
                    precioVentaReal = detalle.precioVentaReal,
                    descuentoAplicado = detalle.descuentoAplicado!!
                )
            }
        )
    }
}