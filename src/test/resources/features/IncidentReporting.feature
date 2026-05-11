Feature: Sistema de Reportes de Incidentes
  Como ciudadano
  Quiero reportar incidentes en mi barrio
  Para que otros usuarios estén informados sobre amenazas de seguridad

  Background:
    Given el sistema está en ejecución
    And la base de datos está limpia
    And existe un perfil de usuario con ID 1

  Scenario: Reportar un nuevo incidente exitosamente
    Given estoy autenticado como usuario con ID 1
    And tengo información del incidente:
      | field       | value                          |
      | type        | ROBBERY                        |
      | title       | Robo en progreso               |
      | description | Robo de vehículo en la Av. 28 |
      | latitude    | -12.0722                       |
      | longitude   | -77.0891                       |
      | address     | Avenida 28, Lima               |
    When envío el reporte del incidente
    Then el incidente debe ser creado exitosamente
    And el estado de respuesta debe ser 201
    And al reporte se le debe asignar un ID
    And el tipo de reporte debe ser "ROBBERY"

  Scenario: Reportar incidente con ubicación geográfica
    Given estoy autenticado como usuario con ID 2
    And tengo información del incidente con coordenadas cerca del centro de Lima:
      | latitude  | -12.0456 |
      | longitude | -77.0289 |
      | address   | Centro de Lima |
    When envío el reporte del incidente
    Then el incidente debe ser creado con ubicación
    And la latitud debe ser -12.0456
    And la longitud debe ser -77.0289

  Scenario: Reportar incidente de forma anónima
    Given estoy autenticado como usuario con ID 3
    And tengo información del incidente marcada como anónima
    When envío el reporte del incidente como anónimo
    Then el reporte del incidente debe ser creado
    And el reporte debe estar marcado como anónimo
    And el ID del usuario no debe ser visible públicamente

  Scenario: Actualizar reporte de incidente existente
    Given existe un reporte de incidente con ID 100 reportado por usuario 1
    When actualizo el incidente con:
      | field       | value                        |
      | title       | Título actualizado           |
      | description | Descripción más detallada    |
    Then el incidente debe ser actualizado
    And el título debe ser "Título actualizado"
    And la marca de tiempo de modificación debe ser reciente

  Scenario: Recuperar incidente por ID
    Given existe un reporte de incidente con ID 200
    When recupero el reporte de incidente por ID 200
    Then el estado de respuesta debe ser 200
    And el ID del incidente debe ser 200
    And todos los detalles del incidente deben estar presentes

  Scenario: Recuperar todos los reportes de un usuario
    Given el usuario con ID 5 ha reportado 3 incidentes
    When recupero todos los reportes de incidentes del usuario 5
    Then la respuesta debe contener 3 incidentes
    And todos los incidentes deben tener ID de usuario 5

  Scenario: Eliminar reporte de incidente
    Given existe un reporte de incidente con ID 300
    When elimino el reporte de incidente con ID 300
    Then el incidente debe ser eliminado
    And el estado de respuesta debe ser 204
    And recuperar el incidente debe devolver 404

  Scenario: Fallar al reportar con tipo de incidente inválido
    Given tengo información del incidente con tipo "INVALID_TYPE"
    When intento enviar el reporte del incidente
    Then el sistema debe rechazar el envío
    And el estado de respuesta debe ser 400
    And el error debe indicar "Tipo de incidente inválido"
