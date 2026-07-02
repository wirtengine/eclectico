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
        // 1. Validar cliente
        val cliente = personaRepository.findById(request.idCliente)
            .orElseThrow { VentaException("Cliente no encontrado") }
        if (cliente.tipo != 'C') throw VentaException("La persona no es un cliente")

        // 2. Validar vendedor (opcional)
        val vendedor = request.idVendedor?.let {
            personaRepository.findById(it)
                .orElseThrow { VentaException("Vendedor no encontrado") }
                .also { if (it.tipo != 'S') throw VentaException("El vendedor no es un socio") }
        }

        // 3. Validar método de pago
        val metodoPago = metodoPagoRepository.findById(request.idMetodoPago)
            .orElseThrow { VentaException("Método de pago no válido") }

        // 4. Validar productos y calcular totales
        val productos = mutableListOf<Producto>()
        var montoTotal = BigDecimal.ZERO
        var costoTotal = BigDecimal.ZERO

        for (detalleReq in request.detalles) {
            val producto = productoRepository.findById(detalleReq.idProducto)
                .orElseThrow { VentaException("Producto no encontrado: ${detalleReq.idProducto}") }
            if (producto.estado?.idEstado != 1) { // 1 = Disponible
                throw VentaException("Producto no disponible: ${producto.codigo}")
            }
            montoTotal += detalleReq.precioVentaReal
            costoTotal += producto.costo
            productos.add(producto)
        }

        // 5. Crear y guardar la venta (los triggers se encargarán de actualizar segmento del cliente)
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

        // 6. Insertar los detalles (el trigger vender_producto() actualizará cada producto a 'Vendido')
        val detalles = request.detalles.mapIndexed { index, detalleReq ->
            DetalleVenta(
                venta = ventaGuardada,
                producto = productos[index],
                precioVentaReal = detalleReq.precioVentaReal,
                descuentoAplicado = detalleReq.descuentoAplicado
            )
        }
        detalleVentaRepository.saveAll(detalles)

        // 7. Construir y devolver la respuesta
        return construirRespuesta(ventaGuardada, detalles)
    }

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