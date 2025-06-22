package com.sharelist.api.config;

import com.sharelist.api.security.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
/**
 * Clase de configuración de seguridad para la aplicación.
 * Define qué endpoints están protegidos, el tipo de autenticación y qué filtros aplicar.
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    // Filtro personalizado que validará el token JWT en cada petición
    private final JwtTokenFilter jwtTokenFilter;

    /**
     * Configura la cadena de filtros de seguridad de Spring.
     * Desactiva CSRF, permite el acceso sin autenticación a algunos endpoints,
     * y protege el resto exigiendo autenticación con JWT.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Desactiva CSRF (Cross-Site Request Forgery), ya que se usa JWT
                .csrf(AbstractHttpConfigurer::disable)
                // Define las reglas de autorización
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos: no requieren autenticación
                        .requestMatchers("/api/users/register", "/api/auth/login").permitAll()
                        // Cualquier otra petición requiere autenticación
                        .anyRequest().authenticated()
                )
                // Desactiva el formulario de login por defecto de Spring Security
                .formLogin(AbstractHttpConfigurer::disable)
                // Añade el filtro JWT antes del filtro de autenticación por usuario y contraseña
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Bean que define el codificador de contraseñas usando BCrypt.
     * Se usará para almacenar contraseñas de forma segura.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Exposición del AuthenticationManager como bean,
     * necesario para realizar autenticaciones manuales (como en AuthController).
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
