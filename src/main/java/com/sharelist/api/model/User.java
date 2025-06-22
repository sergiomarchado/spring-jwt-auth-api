package com.sharelist.api.model;

import jakarta.persistence.*;
import lombok.*;
/**
 * Entidad JPA que representa a un usuario en la base de datos.
 * La tabla se llama "users" y cada instancia de esta clase se mapea a una fila.
 */
@Entity
@Table(name = "users")
@Data // Genera getters, setters, equals, hashCode y toString automáticamente
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los argumentos
@Builder // Permite usar el patrón Builder para construir objetos User
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String email;

    private String fullName;

    private boolean enabled = true;
}
