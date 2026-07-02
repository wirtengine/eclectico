package com.eclectico.backend.config

import com.eclectico.backend.security.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableMethodSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { auth ->
                // Endpoints públicos
                auth.requestMatchers("/api/auth/**").permitAll()
                auth.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                // Endpoints de negocio (requieren autenticación con roles)
                auth.requestMatchers("/api/ventas/**").hasAnyRole("admin", "marketing", "operaciones")
                auth.requestMatchers("/api/productos/**").hasAnyRole("admin", "marketing", "operaciones")
                auth.requestMatchers("/api/reportes/**").hasAnyRole("admin", "marketing", "operaciones")
                auth.requestMatchers("/api/catalogos/**").hasAnyRole("admin", "marketing", "operaciones")
                auth.requestMatchers("/api/personas/**").hasAnyRole("admin", "marketing", "operaciones")

                // Todo lo demás requiere autenticación
                auth.anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}