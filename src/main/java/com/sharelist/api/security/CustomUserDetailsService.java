package com.sharelist.api.security;

import com.sharelist.api.model.User;
import com.sharelist.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

/**
 * Servicio personalizado que implementa UserDetailsService,
 * necesario para que Spring Security pueda cargar los datos del usuario desde la base de datos.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    // Inyección del repositorio de usuarios
    private final UserRepository userRepository;

    /**
     * Este método es llamado automáticamente por Spring Security durante la autenticación.
     * Busca al usuario en la base de datos y construye un objeto UserDetails si lo encuentra.
     *
     * @param username Nombre de usuario proporcionado en el login
     * @return UserDetails con la información del usuario para el sistema de seguridad
     * @throws UsernameNotFoundException si no se encuentra el usuario
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscamos al usuario en la base de datos (por username)
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Devolvemos un objeto UserDetails construido desde nuestra entidad User
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("USER")                  // rol o permisos (se puede mejorar en el futuro)
                .accountExpired(false)                // cuenta no expirada
                .accountLocked(false)                 // cuenta no bloqueada
                .credentialsExpired(false)            // credenciales válidas
                .disabled(!user.isEnabled())          // si está deshabilitado en BD → desactivado aquí
                .build();
    }
}
