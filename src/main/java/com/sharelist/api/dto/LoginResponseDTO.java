package com.sharelist.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * DTO que representa la respuesta devuelta tras un inicio de sesión exitoso.
 * Contiene el token JWT generado para el usuario autenticado.
 */
@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
}

