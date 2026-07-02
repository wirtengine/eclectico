package com.eclectico.backend.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
class SupabaseStorageService(
    @Value("\${supabase.url}") private val supabaseUrl: String,
    @Value("\${supabase.service-role-key}") private val serviceRoleKey: String,
    @Value("\${supabase.bucket}") private val bucket: String
) {
    private val webClient = WebClient.create(supabaseUrl)

    /**
     * Sube una imagen al bucket y devuelve la URL pública.
     */
    fun uploadImage(file: MultipartFile): String {
        val extension = file.originalFilename?.substringAfterLast('.', "jpg") ?: "jpg"
        val fileName = "${UUID.randomUUID()}.$extension"

        webClient.post()
            .uri("/storage/v1/object/$bucket/$fileName")
            .header("Authorization", "Bearer $serviceRoleKey")
            .contentType(MediaType.parseMediaType(file.contentType ?: "image/jpeg"))
            .body(BodyInserters.fromResource(file.resource))
            .retrieve()
            .toBodilessEntity()
            .block() ?: throw RuntimeException("Error al subir la imagen a Supabase")

        // Retornar URL pública (directa, sin transformación)
        return "$supabaseUrl/storage/v1/object/public/$bucket/$fileName"
    }
}