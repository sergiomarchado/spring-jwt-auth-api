# ShareList API 🛒🔐

Una API RESTful construida con **Spring Boot** que implementa autenticación mediante **JWT**, gestión de usuarios y acceso seguro a recursos protegidos. Ideal como base para cualquier sistema que requiera autenticación moderna con token.

---

## 🚀 Características

* 📦 Registro de usuarios con validación
* 🔐 Autenticación con JSON Web Tokens (JWT)
* 🔓 Endpoints públicos y protegidos diferenciados
* ✅ Validación de formularios con mensajes personalizados
* 📄 Gestión global de excepciones
* 🔒 Filtro JWT personalizado que intercepta peticiones
* 📚 Estructura clara con DTOs y separación por capas

---

## 🛠️ Tecnologías utilizadas

| Categoría          | Tecnología                         |
| ------------------ | ---------------------------------- |
| Lenguaje           | Java 17                            |
| Framework          | Spring Boot 3                      |
| Seguridad          | Spring Security + JWT              |
| Persistencia       | Spring Data JPA + MySQL            |
| Validaciones       | Jakarta Validation (JSR-380)       |
| Build Tool         | Maven                              |
| Gestión de depend. | Lombok, JJWT, Spring Boot Starters |

---

## 🔧 Configuración

### Variables sensibles (local)

Las variables de entorno deben definirse en `src/main/resources/application.yml` **(no se sube al repo)**. Ejemplo:

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/sharelist
    username: root
    password: tu_contraseña

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

security:
  jwt:
    secret-key: clave_muy_segura_y_larga
    expire-length: 3600000  # 1 hora
```

Incluimos un archivo `application-example.yml` como plantilla.

---

## 📁 Estructura del proyecto

```
com.sharelist.api
├── config                # Configuración de seguridad
├── controller            # Controladores REST
├── dto                  # Objetos de transferencia (Login, Registro)
├── exception             # Manejador de excepciones y custom errors
├── model                # Entidad User (JPA)
├── repository           # Repositorio de usuarios (Spring Data JPA)
├── security             # Lógica JWT + filtros + servicios
├── service              # Lógica de negocio para registro y auth
└── ShareListApiApplication.java  # Entry point
```

---

## 🔐 Endpoints

| Método | URL                 | Autenticación | Descripción                  |
| ------ | ------------------- | ------------- | ---------------------------- |
| POST   | /api/users/register | ❌ No          | Registro de usuarios         |
| POST   | /api/auth/login     | ❌ No          | Autenticación + devuelve JWT |
| GET    | /api/protected      | ✅ Sí          | Endpoint protegido por JWT   |

---

## ▶️ Cómo ejecutar

1. Clona el repositorio:

```bash
git clone https://github.com/sergiomarchado/spring-jwt-auth-api.git
```

2. Crea tu base de datos MySQL `sharelist`
3. Copia `application-example.yml` y renómbralo como `application.yml`
4. Ajusta tus credenciales y claves JWT
5. Ejecuta desde IntelliJ o con Maven:

```bash
mvn spring-boot:run
```

---

## ✅ Probar la API con Postman

1. **Registro:**

   * URL: `POST /api/users/register`
   * Body (JSON):

```json
{
  "username": "juan",
  "password": "123456",
  "email": "juan@example.com",
  "fullName": "Juan Pérez"
}
```

2. **Login:**

   * URL: `POST /api/auth/login`
   * Body:

```json
{
  "username": "juan",
  "password": "123456"
}
```

* Respuesta:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsIn..."
}
```

3. **Acceder a recurso protegido:**

   * URL: `GET /api/protected`
   * Header:
     `Authorization: Bearer <token>`

---

## 🧪 Test y mejoras futuras

* [ ] Añadir test unitarios con Mockito y JUnit
* [ ] Sistema de roles (ADMIN / USER)
* [ ] Gestión de refresh tokens
* [ ] Documentación con Swagger / OpenAPI

---

## 📄 Licencia

Este proyecto está bajo licencia MIT.

© 2025 Sergio Marchado Ropero — ShareList API
