package com.eclectico.backend.dto

import com.eclectico.backend.entity.*

fun Producto.toResponse(): ProductoResponse {
    return ProductoResponse(
        idProducto = this.idProducto!!,
        codigo = this.codigo ?: "",
        descripcion = this.descripcion,
        linea = this.linea?.nombre ?: "",
        costo = this.costo,
        precioVenta = this.precioVenta,
        precioInicial = this.precioInicial ?: this.precioVenta,
        talla = this.talla,
        medidas = this.medidas,
        color = this.color,
        marcaOriginal = this.marcaOriginal,
        estadoNotas = this.estadoNotas,
        estado = this.estado?.nombre ?: "",
        fechaCompra = this.fechaCompra,
        fechaPublicacion = this.fechaPublicacion,
        diasParaRebaja = this.diasParaRebaja,
        proveedor = this.proveedor?.nombre,
        imagenPrincipal = this.imagenPrincipal,
        imagenes = this.imagenes,
        creadoPor = this.creadoPor?.nombre,
        creadoEn = this.creadoEn?.toString() ?: ""
    )
}

fun Persona.toResponse(): PersonaResponse {
    return PersonaResponse(
        idPersona = this.idPersona!!,
        nombre = this.nombre,
        telefono = this.telefono,
        email = this.email,
        tipo = this.tipo,
        totalCompras = this.totalCompras,
        numCompras = this.numCompras,
        ultimaCompra = this.ultimaCompra,
        segmento = this.segmento,
        fechaRegistro = this.fechaRegistro,
        activo = this.activo
    )
}

fun Linea.toResponse(): LineaResponse {
    return LineaResponse(
        idLinea = this.idLinea!!,
        nombre = this.nombre,
        descripcion = this.descripcion
    )
}

fun EstadoProducto.toResponse(): EstadoProductoResponse {
    return EstadoProductoResponse(
        idEstado = this.idEstado!!,
        nombre = this.nombre
    )
}

fun Proveedor.toResponse(): ProveedorResponse {
    return ProveedorResponse(
        idProveedor = this.idProveedor!!,
        nombre = this.nombre,
        telefono = this.telefono,
        ubicacion = this.ubicacion,
        fechaUltimaVisita = this.fechaUltimaVisita?.toString(),
        calificacion = this.calificacion
    )
}

fun MetodoPago.toResponse(): MetodoPagoResponse {
    return MetodoPagoResponse(
        idMetodo = this.idMetodo!!,
        nombre = this.nombre
    )
}