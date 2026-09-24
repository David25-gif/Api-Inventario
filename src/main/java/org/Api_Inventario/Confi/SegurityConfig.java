package org.Api_Inventario.Confi;

import org.Api_Inventario.Seguridad.Configuracion.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SegurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity) throws Exception {

        return httpSecurity

                // Configuración necesaria para permitir peticiones
                // desde las aplicaciones que consumirán la API.
                .cors(cors ->
                        cors.configurationSource(corsConfigurationSource())
                )

                // La API utiliza JWT y no formularios tradicionales,
                // por lo que no necesitamos protección CSRF.
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth

                        // =====================================================
                        // ENDPOINTS PÚBLICOS
                        // =====================================================

                        // Login y registro no necesitan autenticación.
                        .requestMatchers(
                                "/api/auth/login",
                                "/api/auth/registro",
                                "/error"
                        ).permitAll()

                        // Swagger permanece público para realizar las pruebas.
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()


                        // =====================================================
                        // CATEGORÍAS
                        // =====================================================

                        // Administrador y Cliente pueden consultar categorías.
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/categorias/**"
                        ).hasAnyRole("ADMINISTRADOR", "CLIENTE")

                        // Solo Administrador puede modificar categorías.
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/categorias/**"
                        ).hasRole("ADMINISTRADOR")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/categorias/**"
                        ).hasRole("ADMINISTRADOR")

                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/categorias/**"
                        ).hasRole("ADMINISTRADOR")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/categorias/**"
                        ).hasRole("ADMINISTRADOR")


                        // =====================================================
                        // PRODUCTOS
                        // =====================================================

                        // Ambos roles pueden consultar el catálogo.
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/productos/**"
                        ).hasAnyRole("ADMINISTRADOR", "CLIENTE")

                        // Administración del catálogo.
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/productos/**"
                        ).hasRole("ADMINISTRADOR")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/productos/**"
                        ).hasRole("ADMINISTRADOR")

                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/productos/**"
                        ).hasRole("ADMINISTRADOR")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/productos/**"
                        ).hasRole("ADMINISTRADOR")


                        // =====================================================
                        // INVENTARIO
                        // =====================================================

                        // El módulo de inventario es administrativo.
                        .requestMatchers(
                                "/api/inventario/**"
                        ).hasRole("ADMINISTRADOR")


                        // =====================================================
                        // MOVIMIENTOS DE INVENTARIO
                        // =====================================================

                        // Los movimientos afectan directamente el stock.
                        .requestMatchers(
                                "/api/movimientos-inventario/**"
                        ).hasRole("ADMINISTRADOR")


                        // =====================================================
                        // RESTO DE LA API
                        // =====================================================

                        // Cualquier endpoint que no aparezca arriba requiere
                        // al menos que el usuario haya iniciado sesión.
                        .anyRequest().authenticated()
                )

                // La API no guarda sesiones en el servidor.
                // Cada petición debe llevar su JWT.
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authenticationProvider(authProvider)

                // El filtro JWT se ejecuta antes del filtro estándar
                // de autenticación de Spring Security.
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .build();
    }


    // =========================================================
    // CONFIGURACIÓN CORS
    // =========================================================

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        // Durante desarrollo permitimos cualquier origen.
        configuration.setAllowedOrigins(
                Arrays.asList("*")
        );

        configuration.setAllowedMethods(
                Arrays.asList(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "PATCH",
                        "OPTIONS"
                )
        );

        configuration.setAllowedHeaders(
                Arrays.asList("*")
        );

        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }
}