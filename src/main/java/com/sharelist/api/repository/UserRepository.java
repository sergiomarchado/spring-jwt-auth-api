package com.sharelist.api.repository;

import com.sharelist.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/**
 * Repositorio de acceso a datos para la entidad User.
 * Hereda de JpaRepository, lo que proporciona operaciones CRUD listas para usar.
 * Spring Data JPA implementa automáticamente este repositorio en tiempo de ejecución.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Busca un usuario por su nombre de usuario (username).
     * @param username nombre de usuario a buscar.
     * @return un Optional con el usuario encontrado, o vacío si no existe.
     */
    Optional<User> findByUsername(String username);

    /**
     * Verifica si ya existe un usuario registrado con el mismo nombre de usuario.
     * Usado para evitar registros duplicados.
     * @param username nombre de usuario a verificar.
     * @return true si ya existe, false en caso contrario.
     */
    boolean existsByUsername(String username);

    /**
     * Verifica si ya existe un usuario registrado con el mismo email.
     * También usado para validar unicidad de registros.
     * @param email email a verificar.
     * @return true si ya existe, false si no.
     */
    boolean existsByEmail(String email);
}
