Feature: Sistema de Alertas y Notificaciones
  Como ciudadano
  Quiero recibir alertas sobre incidentes cerca de mi ubicación
  Para estar informado sobre amenazas de seguridad en mi área

  Background:
    Given el sistema está en ejecución
    And la base de datos está limpia
    And existe un perfil de usuario con ID 1 en la ubicación -12.0500, -77.0400

  Scenario: Recibir alerta por incidente cercano
    Given soy un usuario suscrito en la ubicación -12.0500, -77.0400
    When se reporta un nuevo incidente de ROBO en la ubicación -12.0520, -77.0420 (500 metros)
    Then debo recibir una notificación de alerta
    And el tipo de alerta debe ser "INCIDENTE_CERCANO"
    And el mensaje de alerta debe contener "Robo"

  Scenario: Recibir alerta por zona de alto riesgo
    Given soy un usuario suscrito en la ubicación -12.0500, -77.0400
    When el nivel de riesgo de la zona cercana cambia a "CRÍTICO"
    Then debo recibir una alerta de ZONA_ALTO_RIESGO
    And la alerta debe incluir las coordenadas de la zona
    And la alerta debe indicar el nuevo nivel de riesgo

  Scenario: No recibir alerta por incidente distante
    Given soy un usuario suscrito en la ubicación -12.0500, -77.0400
    When se reporta un incidente en la ubicación -12.2500, -77.2400 (25 kilómetros)
    Then NO debo recibir una alerta para este incidente

  Scenario: Entrega de notificación push
    Given se ha generado una nueva alerta para el usuario 1
    And el usuario tiene las notificaciones push habilitadas
    When el sistema de notificaciones procesa la alerta
    Then se debe enviar una notificación push al dispositivo del usuario
    And la notificación debe contener los detalles de la alerta

  Scenario: Entrega de notificación por email
    Given se ha generado una alerta crítica para el usuario 2
    And el usuario tiene las notificaciones por email habilitadas
    When el sistema de notificaciones envía notificaciones por email
    Then se debe enviar un email al correo registrado del usuario
    And el email debe incluir detalles del incidente y recomendaciones

  Scenario: Estado de lectura de alerta
    Given existe una alerta para el usuario 3
    And la alerta no ha sido marcada como leída
    When el usuario ve la alerta
    Then la alerta debe ser marcada como leída
    And las consultas posteriores deben mostrar is_read = true

  Scenario: Archivar alertas antiguas
    Given el usuario 4 tiene 100 alertas en el sistema
    And algunas alertas tienen más de 30 días
    When se ejecuta el trabajo de archivo
    Then las alertas más antiguas de 30 días deben ser archivadas
    And las alertas activas deben seguir siendo accesibles

  Scenario: Desactivar notificaciones temporalmente
    Given el usuario 5 tiene notificaciones activas habilitadas
    When el usuario desactiva las notificaciones
    Then no se deben enviar nuevas alertas al usuario 5
    And las alertas existentes deben permanecer en el sistema
    When el usuario reactiva las notificaciones
    Then las nuevas alertas deben reanudarse
