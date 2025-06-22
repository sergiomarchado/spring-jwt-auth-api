package com.sharelist.api.exception;

/**
 * Excepción personalizada que se lanza cuando se intenta registrar
 * un usuario con un nombre de usuario o correo electrónico ya existente.
 * Hereda de RuntimeException para no requerir manejo obligatorio (checked).
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
