package com.eclectico.backend.controller

import com.eclectico.backend.service.CatalogoService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/catalogos")
class CatalogoController(private val catalogoService: CatalogoService) {

    @GetMapping("/lineas")
    fun lineas() = catalogoService.listarLineas()

    @GetMapping("/estados-producto")
    fun estadosProducto() = catalogoService.listarEstados()

    @GetMapping("/proveedores")
    fun proveedores() = catalogoService.listarProveedores()

    @GetMapping("/metodos-pago")
    fun metodosPago() = catalogoService.listarMetodosPago()
}