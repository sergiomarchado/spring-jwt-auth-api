package com.sharelist.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST que expone un endpoint protegido.
 * Sirve para comprobar que la autenticación mediante JWT funciona correctamente.
 */
@RestController
@RequestMapping("/api/protected")      // Ruta base para los endpoints protegidos
public class ProtectedController {

    /**
     * Endpoint GET que solo puede ser accedido con un token JWT válido.
     *
     * @return Mensaje indicando que se accedió correctamente.
     */
    @GetMapping
    public String getProtectedMessage() {
        return "¡Accediste correctamente a un recurso protegido!";
    }
}
