package com.sharelist.api.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Componente encargado de la creación, validación y parsing de tokens JWT.
 * Utiliza la biblioteca JJWT (Java JWT) para gestionar la firma y decodificación de tokens.
 */
@Component
public class JwtTokenProvider {

    // Clave secreta definida en application.yml para firmar los tokens
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    // Tiempo de expiración del token en milisegundos (también configurado en application.yml)
    @Value("${security.jwt.expire-length}")
    private long validityInMilliseconds;

    // Clave firmada internamente para uso de la API de JWT
    private Key key;

    /**
     * Inicializa la clave HMAC usando la clave secreta. Este método se ejecuta después de la inyección de dependencias.
     */
    @PostConstruct
    protected void init() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    /**
     * Genera un token JWT válido para el usuario dado.
     *
     * @param username nombre de usuario (se almacenará como subject en el token)
     * @return token JWT firmado y con fecha de expiración
     */
    public String createToken(String username) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(username)       // asignamos el subject (quién es el usuario)
                .setIssuedAt(now)           // fecha de creación
                .setExpiration(validity)    // fecha de expiración
                .signWith(key, SignatureAlgorithm.HS256)  // tipo de firma y clave
                .compact();
    }

    /**
     * Extrae el nombre de usuario (subject) del token JWT.
     *
     * @param token JWT válido
     * @return nombre de usuario extraído del token
     */
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)      // lanza excepción si el token es inválido
                .getBody()
                .getSubject();
    }

    /**
     * Verifica si un token es válido: firma correcta y no ha expirado.
     *
     * @param token JWT recibido
     * @return true si el token es válido, false si está corrupto o expirado
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Token inválido, expirado o manipulado
            return false;
        }
    }
}
