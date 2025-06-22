package com.sharelist.api.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
/**
 * DTO que representa los datos recibidos en una solicitud de inicio de sesión.
 * Contiene el nombre de usuario y la contraseña introducidos por el usuario.
 * Se utilizan anotaciones de validación para asegurar que no lleguen vacíos.
 */
@Data
public class LoginRequestDTO {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
