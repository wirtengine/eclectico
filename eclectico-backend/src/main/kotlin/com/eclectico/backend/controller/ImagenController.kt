package com.eclectico.backend.controller

import com.eclectico.backend.service.SupabaseStorageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/imagenes")
class ImagenController(private val supabaseStorageService: SupabaseStorageService) {

    @PostMapping("/upload")
    fun uploadImage(@RequestParam("file") file: MultipartFile): ResponseEntity<Map<String, String>> {
        val url = supabaseStorageService.uploadImage(file)
        return ResponseEntity.ok(mapOf("url" to url))
    }
}