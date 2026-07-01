# UrbanVoice Backend

Backend para la aplicacion UrbanVoice - Reporte ciudadano de incidentes. Construido con Java 21 y Spring Boot 3.3.4.

## Arquitectura

El proyecto sigue una arquitectura hexagonal (DDD) con los siguientes bounded contexts:
- **Profiles**: Gestión de perfiles de usuario
- **Reports**: Reporte de incidentes ciudadanos
- **Locations**: Ubicaciones y zonas de riesgo
- **Notifications**: Sistema de notificaciones y alertas
- **Districts**: Gestion de distritos y niveles de riesgo
- **IAM**: Autenticacion de usuarios y seguridad con JWT

## Endpoints API

### Profiles (`/api/v1/profiles`)
- `POST /` - Crear perfil de usuario
- `GET /{id}` - Obtener perfil por ID
- `GET /email/{email}` - Obtener perfil por email
- `PUT /{id}` - Actualizar perfil
- `DELETE /{id}` - Eliminar perfil

### Reports (`/api/v1/reports`)
- `POST /` - Crear reporte de incidente
- `GET /{id}` - Obtener reporte por ID
- `GET /user/{userId}` - Obtener reportes por usuario
- `GET /nearby` - Obtener reportes cercanos
- `PUT /{id}` - Actualizar reporte
- `DELETE /{id}` - Eliminar reporte
- `POST /{id}/upvote` - Votar positivo
- `POST /{id}/downvote` - Votar negativo

### Locations (`/api/v1/locations`)
- `POST /` - Crear ubicación
- `GET /` - Listar ubicaciones
- `GET /{id}` - Obtener ubicación por ID
- `GET /nearby` - Ubicaciones cercanas
- `GET /district/{district}` - Ubicaciones por distrito
- `GET /dangerous` - Ubicaciones peligrosas
- `DELETE /{id}` - Eliminar ubicación

## Requisitos
- JDK 21
- Maven 3.6+ o Maven Wrapper incluido en el proyecto
- PostgreSQL

## Ejecutar
```bash
mvn spring-boot:run
```
