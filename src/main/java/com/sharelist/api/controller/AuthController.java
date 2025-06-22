package com.sharelist.api.controller;

import com.sharelist.api.dto.LoginRequestDTO;
import com.sharelist.api.dto.LoginResponseDTO;
import com.sharelist.api.model.User;
import com.sharelist.api.repository.UserRepository;
import com.sharelist.api.security.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
/**
 * Controlador encargado del proceso de autenticación de usuarios.
 * Gestiona el inicio de sesión y la generación del token JWT.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    /**
     * Endpoint de inicio de sesión. Recibe las credenciales y devuelve un token JWT si son válidas.
     *
     * @param dto Objeto que contiene el username y password del usuario.
     * @return Token JWT en caso de éxito o mensaje de error en caso de fallo.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO dto) {
        try {
            // Validación previa de existencia del usuario (opcional pero clara)
            User user = userRepository.findByUsername(dto.getUsername())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Intenta autenticar con el AuthenticationManager de Spring Security
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
            );

            // Si la autenticación es exitosa, se genera el token JWT
            String token = jwtTokenProvider.createToken(user.getUsername());

            // Se devuelve el token en una respuesta estructurada
            return ResponseEntity.ok(new LoginResponseDTO(token));

        } catch (AuthenticationException e) {
            // Si las credenciales no son válidas
            return ResponseEntity.status(401)
                    .body(new ErrorResponse("Credenciales inválidas"));
        } catch (RuntimeException e) {
            // Si el usuario no existe u otro error controlado
            return ResponseEntity.status(404)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * DTO interno usado para respuestas de error simples.
     *
     * @param error Mensaje de error.
     */
    record ErrorResponse(String error) {}
}
