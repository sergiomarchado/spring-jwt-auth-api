package com.sharelist.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
/**
 * Se utiliza para capturar y validar los datos recibidos en la petición de registro.
 */
@Data
public class UserRegistrationDTO {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @Email(message = "El correo no tiene un formato válido")
    private String email;

    @NotBlank(message = "El nombre completo es obligatorio")
    private String fullName;
}
