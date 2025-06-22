package com.sharelist.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
/**
 * Clase que maneja de forma centralizada las excepciones lanzadas por los controladores.
 *
 * Anotada con @RestControllerAdvice para que se aplique globalmente en todo el proyecto.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja los casos en los que se intenta registrar un usuario que ya existe.
     *
     * @param ex excepción personalizada lanzada desde el servicio de usuarios.
     * @return respuesta HTTP con código 409 (CONFLICT) y el mensaje del error.
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }


    /**
     * Maneja errores de validación cuando los datos enviados no cumplen las restricciones.
     *
     * @param ex excepción lanzada automáticamente por Spring al fallar una validación con @Valid/@NotBlank.
     * @return mapa con nombre del campo y mensaje de error, y un código 400 (BAD REQUEST).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Captura cualquier otra excepción no manejada de forma específica.
     *
     * @param ex la excepción lanzada.
     * @return respuesta con código 500 (INTERNAL SERVER ERROR) y un mensaje genérico.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ha ocurrido un error inesperado: " + ex.getMessage());
    }
}
