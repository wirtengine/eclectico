package com.eclectico.backend.repository

import com.eclectico.backend.entity.Producto
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ProductoRepository : JpaRepository<Producto, UUID> {
    fun findByCodigo(codigo: String): Producto?
    fun findByEstadoIdEstado(idEstado: Int): List<Producto>

    fun findByEstadoIdEstadoAndDescripcionContainingIgnoreCaseOrCodigoContaining(
        idEstado: Int,
        descripcion: String,
        codigo: String
    ): List<Producto>
}