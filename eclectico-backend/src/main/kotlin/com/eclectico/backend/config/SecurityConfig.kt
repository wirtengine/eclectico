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
                // 1. Endpoints públicos
                auth.requestMatchers("/api/auth/**").permitAll()
                auth.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                // 2. Búsquedas específicas (deben ir antes de las reglas generales)
                auth.requestMatchers("/api/productos/buscar").hasAnyRole("admin", "marketing", "operaciones")
                auth.requestMatchers("/api/personas/buscar").hasAnyRole("admin", "marketing", "operaciones")

                // 3. Rutas generales de negocio
                auth.requestMatchers("/api/ventas/**").hasAnyRole("admin", "marketing", "operaciones")
                auth.requestMatchers("/api/productos/**").hasAnyRole("admin", "marketing", "operaciones")
                auth.requestMatchers("/api/reportes/**").hasAnyRole("admin", "marketing", "operaciones")
                auth.requestMatchers("/api/catalogos/**").hasAnyRole("admin", "marketing", "operaciones")
                auth.requestMatchers("/api/personas/**").hasAnyRole("admin", "marketing", "operaciones")

                // 4. Gestión de socios (si se implementa más adelante)
                auth.requestMatchers("/api/personas/socios/**").hasRole("admin")

                // 5. Paramétricas (solo administradores)
                auth.requestMatchers("/api/lineas/**").hasRole("admin")
                auth.requestMatchers("/api/metodos-pago/**").hasRole("admin")
                auth.requestMatchers("/api/estados-producto/**").hasRole("admin")
                auth.requestMatchers("/api/roles/**").hasRole("admin")

                // 6. Otras secciones administrativas
                auth.requestMatchers("/api/proveedores/**").hasRole("admin")
                auth.requestMatchers("/api/gastos/**").hasAnyRole("admin", "operaciones")
                auth.requestMatchers("/api/notificaciones/**").hasAnyRole("admin", "marketing")
                auth.requestMatchers("/api/auditoria/**").hasRole("admin")
                auth.requestMatchers("/api/configuracion/**").hasRole("admin")

                // 7. Cualquier otra petición requiere autenticación
                auth.anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}