# UrbanVoice - Backend

Este repositorio contiene el backend de **UrbanVoice**, una aplicación orientada al reporte ciudadano de incidentes urbanos. El sistema permite gestionar usuarios, perfiles, reportes de incidentes, ubicaciones, alertas y notificaciones mediante una API REST desarrollada con Spring Boot.

## Descripción

UrbanVoice Backend es el servicio principal encargado de procesar la lógica de negocio de la aplicación móvil UrbanVoice. Su objetivo es brindar una API REST que permita a la app Android registrar y consultar reportes ciudadanos, administrar perfiles de usuario, manejar ubicaciones y generar alertas relacionadas con incidentes urbanos.

El backend está organizado siguiendo una estructura basada en **Domain-Driven Design (DDD)** y separación por capas, permitiendo que el proyecto sea más escalable, mantenible y fácil de extender.

## Tecnologías utilizadas

* Java 21
* Spring Boot 3.3.4
* Spring Web
* Spring Data JPA
* Spring Security
* JWT
* PostgreSQL
* Springdoc OpenAPI / Swagger
* Maven
* Lombok
* HATEOAS
* Docker
* JUnit
* Cucumber
* H2 Database para pruebas

## Arquitectura del proyecto

El proyecto está organizado por bounded contexts, separando cada módulo principal de la aplicación.

```text
src/
└── main/
    └── java/
        └── com/
            └── upc/
                └── pre/
                    └── urbanvoiceapp/
                        ├── districts/
                        ├── iam/
                        ├── locations/
                        ├── notifications/
                        ├── profiles/
                        ├── reports/
                        ├── shared/
                        └── UrbanVoiceApplication.java
```

## Módulos principales

### IAM

Módulo encargado de la autenticación de usuarios. Permite iniciar sesión y generar tokens JWT para el acceso a los servicios protegidos.

### Profiles

Módulo encargado de la gestión de perfiles de usuario. Permite crear, consultar, actualizar y eliminar perfiles.

### Reports

Módulo principal del sistema. Gestiona los reportes ciudadanos de incidentes urbanos, incluyendo la creación, consulta, actualización, eliminación y búsqueda de reportes cercanos.

### Locations

Módulo encargado de gestionar ubicaciones exactas asociadas a reportes o alertas. Permite registrar coordenadas, direcciones y distritos.

### Notifications

Módulo encargado de gestionar alertas y notificaciones para los usuarios.

### Districts

Módulo relacionado con la gestión de distritos dentro del sistema.

### Shared

Contiene componentes compartidos utilizados por los distintos módulos del backend.

## Funcionalidades implementadas

* Autenticación de usuarios mediante JWT.
* Creación y gestión de perfiles de usuario.
* Consulta de perfiles por ID.
* Consulta de perfiles por correo electrónico.
* Creación de reportes de incidentes ciudadanos.
* Consulta de reportes por ID.
* Consulta de reportes por usuario.
* Consulta de todos los reportes registrados.
* Búsqueda de reportes cercanos mediante latitud, longitud y radio.
* Actualización de reportes.
* Eliminación de reportes.
* Registro de ubicaciones.
* Consulta de ubicaciones por ID.
* Consulta de todas las ubicaciones.
* Búsqueda de ubicaciones cercanas.
* Consulta de ubicaciones por distrito.
* Eliminación de ubicaciones.
* Creación de alertas.
* Consulta de alertas por ID.
* Consulta de alertas por usuario.
* Consulta de todas las alertas.
* Eliminación de alertas.
* Configuración de documentación API con Swagger/OpenAPI.
* Configuración de base de datos PostgreSQL.
* Configuración para ejecución con Docker.

## Requisitos previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

* JDK 21
* Maven 3.6 o superior
* PostgreSQL
* Git
* Docker, opcional

## Configuración de base de datos

El backend está configurado para usar PostgreSQL. Por defecto, la aplicación intenta conectarse a la siguiente base de datos local:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/urbanvoice
spring.datasource.username=postgres
spring.datasource.password=1234
```

También se pueden usar variables de entorno para configurar la conexión:

```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/urbanvoice
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=1234
JWT_SECRET=UrbanVoiceSecretKey2024SuperSeguraParaFirmarTokensJWTCon256Bits
```

## Ejecución local

### 1. Clonar el repositorio

```bash
git clone https://github.com/urbanvoice-3248-dispo-moviles/Backend-UrbanVoice.git
```

### 2. Entrar al proyecto

```bash
cd Backend-UrbanVoice
```

### 3. Crear la base de datos en PostgreSQL

```sql
CREATE DATABASE urbanvoice;
```

### 4. Ejecutar el backend

En Windows:

```bash
mvnw.cmd spring-boot:run
```

En Linux o macOS:

```bash
./mvnw spring-boot:run
```

También se puede ejecutar usando Maven instalado globalmente:

```bash
mvn spring-boot:run
```

### 5. Verificar ejecución

El backend se ejecuta por defecto en:

```text
http://localhost:8080
```

La API utiliza como prefijo base:

```text
/api/v1
```

## Documentación de API

El proyecto incluye Springdoc OpenAPI para documentar los servicios REST.

Una vez iniciado el backend, la documentación puede revisarse en:

```text
http://localhost:8080/swagger-ui/index.html
```

También se puede consultar la especificación OpenAPI en:

```text
http://localhost:8080/v3/api-docs
```

## Endpoints principales

### Authentication

Base path:

```text
/api/v1/auth
```

| Método | Endpoint | Descripción                            |
| ------ | -------- | -------------------------------------- |
| POST   | `/login` | Inicia sesión y devuelve un token JWT. |

### Profiles

Base path:

```text
/api/v1/profiles
```

| Método | Endpoint         | Descripción                               |
| ------ | ---------------- | ----------------------------------------- |
| POST   | `/`              | Crea un nuevo perfil de usuario.          |
| GET    | `/{id}`          | Obtiene un perfil por ID.                 |
| GET    | `/email/{email}` | Obtiene un perfil por correo electrónico. |
| PUT    | `/{id}`          | Actualiza un perfil existente.            |
| DELETE | `/{id}`          | Elimina un perfil.                        |

### Reports

Base path:

```text
/api/v1/reports
```

| Método | Endpoint         | Descripción                                                 |
| ------ | ---------------- | ----------------------------------------------------------- |
| POST   | `/`              | Crea un nuevo reporte de incidente.                         |
| GET    | `/{id}`          | Obtiene un reporte por ID.                                  |
| GET    | `/user/{userId}` | Obtiene los reportes asociados a un usuario.                |
| GET    | `/all`           | Obtiene todos los reportes registrados.                     |
| GET    | `/nearby`        | Obtiene reportes cercanos usando latitud, longitud y radio. |
| PUT    | `/{id}`          | Actualiza un reporte existente.                             |
| DELETE | `/{id}`          | Elimina un reporte.                                         |

Ejemplo de consulta de reportes cercanos:

```text
GET /api/v1/reports/nearby?latitude=-12.0464&longitude=-77.0428&radiusInKm=5
```

Para crear un reporte, el backend espera recibir el ID del usuario en el header:

```text
X-User-ID: 1
```

### Locations

Base path:

```text
/api/v1/locations
```

| Método | Endpoint               | Descripción                       |
| ------ | ---------------------- | --------------------------------- |
| POST   | `/`                    | Crea una nueva ubicación.         |
| GET    | `/`                    | Obtiene todas las ubicaciones.    |
| GET    | `/{id}`                | Obtiene una ubicación por ID.     |
| GET    | `/nearby`              | Obtiene ubicaciones cercanas.     |
| GET    | `/district/{district}` | Obtiene ubicaciones por distrito. |
| DELETE | `/{id}`                | Elimina una ubicación.            |

Ejemplo de consulta de ubicaciones cercanas:

```text
GET /api/v1/locations/nearby?latitude=-12.0464&longitude=-77.0428&radiusInKm=5
```

### Alerts

Base path:

```text
/api/v1/alerts
```

| Método | Endpoint         | Descripción                                 |
| ------ | ---------------- | ------------------------------------------- |
| POST   | `/`              | Crea una nueva alerta.                      |
| GET    | `/`              | Obtiene todas las alertas.                  |
| GET    | `/{id}`          | Obtiene una alerta por ID.                  |
| GET    | `/user/{userId}` | Obtiene las alertas asociadas a un usuario. |
| DELETE | `/{id}`          | Elimina una alerta por ID.                  |
| DELETE | `/`              | Elimina todas las alertas.                  |

## Variables de entorno

El proyecto permite configurar valores importantes mediante variables de entorno:

| Variable                     | Descripción                           | Valor por defecto                             |
| ---------------------------- | ------------------------------------- | --------------------------------------------- |
| `PORT`                       | Puerto de ejecución del backend.      | `8080`                                        |
| `SPRING_DATASOURCE_URL`      | URL de conexión a PostgreSQL.         | `jdbc:postgresql://localhost:5432/urbanvoice` |
| `SPRING_DATASOURCE_USERNAME` | Usuario de PostgreSQL.                | `postgres`                                    |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña de PostgreSQL.             | `1234`                                        |
| `JWT_SECRET`                 | Clave secreta para firmar tokens JWT. | Valor definido en `application.properties`    |

## Ejecución con Docker

El repositorio incluye un `Dockerfile`, por lo que el backend puede construirse y ejecutarse como contenedor.

### Construir imagen

```bash
docker build -t urbanvoice-backend .
```

### Ejecutar contenedor

```bash
docker run -p 8080:8080 urbanvoice-backend
```

Si se usa una base de datos externa, se recomienda pasar las variables de entorno:

```bash
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/urbanvoice \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=1234 \
  -e JWT_SECRET=UrbanVoiceSecretKey2024SuperSeguraParaFirmarTokensJWTCon256Bits \
  urbanvoice-backend
```

## Pruebas

El proyecto incluye dependencias para pruebas unitarias e integración usando JUnit, H2 y Cucumber.

Para ejecutar las pruebas:

```bash
mvn test
```

O usando Maven Wrapper:

```bash
./mvnw test
```

En Windows:

```bash
mvnw.cmd test
```

## Integración con el frontend Android

Este backend es consumido por la aplicación móvil Android de UrbanVoice.

Repositorio del frontend:

```text
https://github.com/urbanvoice-3248-dispo-moviles/Front-End-Android
```

Durante el desarrollo local con emulador Android, el frontend puede conectarse al backend usando:

```text
http://10.0.2.2:8080/api/v1/
```

Esta dirección permite que el emulador Android acceda al servidor local ejecutado en la computadora.

## Estado actual del proyecto

Actualmente el backend cuenta con una base funcional para los módulos principales del sistema UrbanVoice. Se han implementado servicios REST para autenticación, perfiles, reportes, ubicaciones y alertas, además de configuración de seguridad, persistencia y documentación API.

Entre los avances principales se encuentran:

* Organización del backend por bounded contexts.
* Separación por capas de dominio, aplicación, infraestructura e interfaces REST.
* Endpoints REST para los principales recursos del sistema.
* Persistencia con PostgreSQL y Spring Data JPA.
* Seguridad con Spring Security y JWT.
* Documentación de API con OpenAPI/Swagger.
* Configuración mediante variables de entorno.
* Soporte para ejecución local y con Docker.
* Dependencias para pruebas automatizadas.

## Mejoras futuras

* Agregar más pruebas unitarias e integración.
* Completar documentación técnica de cada módulo.
* Agregar ejemplos de request y response para cada endpoint.
* Implementar control de roles y permisos más detallado.
* Mejorar el manejo global de errores.
* Agregar validaciones más completas en los recursos de entrada.
* Configurar perfiles de ejecución para desarrollo, pruebas y producción.
* Agregar pipeline de CI/CD.
* Agregar docker-compose para levantar backend y base de datos juntos.
* Mejorar la documentación de despliegue en la nube.


#Proyecto en desarrollo y conclusiones 

## Conclusión

UrbanVoice Backend proporciona la API principal para la aplicación móvil UrbanVoice. Su estructura modular, el uso de Spring Boot, PostgreSQL, JWT y una organización basada en DDD permiten construir una base sólida para una solución de reportes ciudadanos escalable y mantenible.
