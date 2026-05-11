Feature: Gestión de Perfiles de Usuario
  Como ciudadano
  Quiero gestionar mi perfil de usuario en el sistema UrbanVoice
  Para poder reportar incidentes y recibir notificaciones

  Background:
    Given el sistema está en ejecución
    And la base de datos está limpia

  Scenario: Crear un nuevo perfil de usuario exitosamente
    Given tengo información válida de usuario:
      | field         | value                    |
      | name          | Juan                     |
      | lastName      | García                   |
      | email         | juan.garcia@example.com  |
      | phoneNumber   | +51987654321             |
      | age           | 28                       |
      | profileImage  | https://example.com/img  |
    When creo un nuevo perfil de usuario
    Then el perfil de usuario debe ser creado exitosamente
    And el estado de respuesta debe ser 201
    And el perfil debe tener un ID asignado
    And el email debe ser "juan.garcia@example.com"

  Scenario: Fallar al crear usuario con email duplicado
    Given ya existe un perfil de usuario con email "maria.lopez@example.com"
    And tengo información de usuario con email "maria.lopez@example.com"
    When intento crear un nuevo perfil de usuario
    Then el sistema debe rechazar la creación
    And el estado de respuesta debe ser 409
    And el mensaje de error debe indicar "El perfil de usuario ya existe"

  Scenario: Actualizar información del perfil de usuario
    Given existe un perfil de usuario con email "carlos.morales@example.com"
    When actualizo el perfil de usuario con:
      | field       | value           |
      | phoneNumber | +51912345678    |
      | age         | 30              |
    Then el perfil de usuario debe ser actualizado exitosamente
    And el número de teléfono debe ser "+51912345678"
    And la edad debe ser 30

  Scenario: Recuperar perfil de usuario por email
    Given existe un perfil de usuario con:
      | email | ana.santiago@example.com |
      | name  | Ana                      |
    When recupero el perfil de usuario por email "ana.santiago@example.com"
    Then el estado de respuesta debe ser 200
    And el nombre del perfil debe ser "Ana"
    And el email del perfil debe ser "ana.santiago@example.com"

  Scenario: Fallar al recuperar perfil de usuario inexistente
    Given no existe un perfil de usuario con email "nonexistent@example.com"
    When recupero el perfil de usuario por email "nonexistent@example.com"
    Then el estado de respuesta debe ser 404
    And el mensaje de error debe indicar "Perfil de usuario no encontrado"

  Scenario: Eliminar perfil de usuario
    Given existe un perfil de usuario con email "toDelete@example.com"
    When elimino el perfil de usuario
    Then el perfil de usuario debe ser eliminado
    And el estado de respuesta debe ser 204
    And recuperar el perfil debe devolver 404
