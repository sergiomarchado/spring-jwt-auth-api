package com.sharelist.api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
/**
 * Filtro personalizado que intercepta cada petición HTTP una sola vez (OncePerRequestFilter)
 * para verificar si contiene un JWT válido en la cabecera Authorization.
 */
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;

    /**
     * Método principal del filtro. Se ejecuta en cada petición y verifica el JWT si es necesario.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        System.out.println("Intercepted request to path: " + path);

        // Ignoramos validación para rutas públicas de registro y login
        if (path.startsWith("/api/users/register") || path.startsWith("/api/auth/login")) {
            System.out.println("Public endpoint, skipping JWT validation");
            filterChain.doFilter(request, response);
            return;
        }

        // Obtenemos la cabecera Authorization
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Si no está o no comienza con "Bearer ", continuamos sin autenticar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("Missing or invalid Authorization header");
            filterChain.doFilter(request, response);
            return;
        }

        // Extraemos el token quitando el prefijo "Bearer "
        final String token = authHeader.substring(7);
        final String username = jwtTokenProvider.getUsername(token);
        System.out.println("Token detected, username extracted: " + username);

        // Si el usuario no está aún autenticado y el token es válido
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtTokenProvider.validateToken(token)) {
                System.out.println("Token is valid. Setting authentication.");
                // Creamos un objeto de autenticación con los datos del usuario
                var authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Establecemos el usuario autenticado en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                System.out.println("Token is invalid.");
            }
        }

        // Continuamos con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
