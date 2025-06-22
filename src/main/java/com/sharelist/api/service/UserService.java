package com.sharelist.api.service;

import com.sharelist.api.dto.UserRegistrationDTO;
import com.sharelist.api.exception.UserAlreadyExistsException;
import com.sharelist.api.model.User;
import com.sharelist.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;
/**
 * Servicio encargado de la lógica de negocio relacionada con los usuarios.
 * Se encarga, entre otras cosas, del registro de nuevos usuarios.
 */
@Service
@RequiredArgsConstructor    // Genera automáticamente un constructor con los campos final
public class UserService {

    // Repositorio que interactúa con la base de datos para operaciones con usuarios
    private final UserRepository userRepository;

    // Componente que se utiliza para cifrar contraseñas antes de guardarlas
    private final PasswordEncoder passwordEncoder;

    /**
     * Registra un nuevo usuario en la base de datos si no existe previamente.
     *
     * @param dto Datos de registro del usuario (username, email, password, fullName)
     * @return El objeto User guardado en la base de datos
     * @throws UserAlreadyExistsException si el username o email ya están registrados
     */
    public User register(UserRegistrationDTO dto) {

        // Verificación: nombre de usuario ya registrado
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new UserAlreadyExistsException("El nombre de usuario ya está en uso.");
        }

        // Verificación: email ya registrado
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException("El correo electrónico ya está en uso.");
        }

        try {
            // Construcción del objeto User utilizando patrón Builder (de Lombok)
            User user = User.builder()
                    .username(dto.getUsername())
                    .password(passwordEncoder.encode(dto.getPassword()))  // Cifrado seguro
                    .email(dto.getEmail())
                    .fullName(dto.getFullName())
                    .enabled(true)
                    .build();

            // Guardado en la base de datos
            return userRepository.save(user);

        } catch (DataIntegrityViolationException e) {
            // Este error puede darse si no se respetan las restricciones únicas en BD
            throw new UserAlreadyExistsException("El usuario ya existe con username: " + dto.getUsername());
        }
    }
}
