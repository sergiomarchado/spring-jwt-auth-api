package com.sharelist.api.controller;

import com.sharelist.api.dto.UserRegistrationDTO;
import com.sharelist.api.model.User;
import com.sharelist.api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * Controlador REST encargado de gestionar las operaciones relacionadas con usuarios.
 * En este caso, permite registrar nuevos usuarios.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor       // Genera automáticamente el constructor con los campos finales requeridos
public class UserController {

    // Inyección del servicio que contiene la lógica de registro de usuarios
    private final UserService userService;

    /**
     * Endpoint para registrar un nuevo usuario.
     *
     * @param dto Objeto con los datos necesarios para el registro (username, password, email, fullName)
     * @return ResponseEntity con el usuario creado y status 200 OK
     * Validaciones:
     * - Se utiliza @Valid para activar las validaciones definidas en el DTO.
     * - Si hay errores de validación, se gestionan automáticamente por el GlobalExceptionHandler.
     */
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserRegistrationDTO dto) {
        // Delegamos la lógica de registro al servicio correspondiente
        User user = userService.register(dto);
        return ResponseEntity.ok(user);   // Devolvemos el usuario registrado como respuesta
    }
}
