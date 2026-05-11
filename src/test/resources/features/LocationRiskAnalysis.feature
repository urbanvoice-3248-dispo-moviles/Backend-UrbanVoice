Feature: Análisis de Riesgo de Ubicación
  Como ciudadano
  Quiero identificar áreas de alto riesgo en mi ciudad
  Para poder evitar zonas peligrosas y mantenerme seguro

  Background:
    Given el sistema está en ejecución
    And la base de datos está limpia

  Scenario: Identificar ubicación de bajo riesgo
    Given la ubicación en latitud -12.0722 y longitud -77.0891 no tiene incidentes recientes
    When consulto el nivel de riesgo para esta ubicación
    Then el nivel de riesgo debe ser "BAJO"
    And la puntuación de riesgo debe ser 1

  Scenario: Identificar ubicación de riesgo medio
    Given la ubicación en latitud -12.0456 y longitud -77.0289 tiene 5 incidentes recientes
    When consulto el nivel de riesgo para esta ubicación
    Then el nivel de riesgo debe ser "MEDIO"
    And la puntuación de riesgo debe ser 2

  Scenario: Identificar ubicación de alto riesgo
    Given la ubicación en latitud -12.0500 y longitud -77.0400 tiene 15 incidentes recientes en la última semana
    When consulto el nivel de riesgo para esta ubicación
    Then el nivel de riesgo debe ser "ALTO"
    And la puntuación de riesgo debe ser 3

  Scenario: Identificar ubicación de riesgo crítico
    Given la ubicación en latitud -12.0600 y longitud -77.0350 tiene 25+ incidentes recientes en los últimos 3 días
    When consulto el nivel de riesgo para esta ubicación
    Then el nivel de riesgo debe ser "CRÍTICO"
    And la puntuación de riesgo debe ser 4

  Scenario: Encontrar ubicaciones cercanas con incidentes
    Given estoy en la ubicación con latitud -12.0500 y longitud -77.0400
    And hay 5 reportes de incidentes dentro de 2 kilómetros
    When busco ubicaciones con incidentes dentro de 2 kilómetros
    Then la respuesta debe contener 5 incidentes cercanos
    And todos los resultados deben estar dentro de 2 kilómetros de mi ubicación

  Scenario: Obtener ubicaciones por nivel de riesgo
    Given el sistema tiene ubicaciones con diferentes niveles de riesgo
    When consulto todas las ubicaciones de riesgo ALTO
    Then la respuesta debe incluir solo ubicaciones de riesgo ALTO
    And cada ubicación debe tener incident_count >= 10

  Scenario: Rastrear actualizaciones de ubicación
    Given una ubicación con ID 1 tiene nivel de riesgo "BAJO"
    And se reportan nuevos incidentes cerca de esta ubicación
    When se actualiza el cálculo de riesgo
    Then el nivel de riesgo de la ubicación debe ser recalculado
    And el nuevo nivel de riesgo podría ser más alto que antes

  Scenario: Fallar al obtener nivel de riesgo para coordenadas inválidas
    Given proporciono coordenadas inválidas con latitud 181 y longitud 181
    When consulto el nivel de riesgo
    Then el estado de respuesta debe ser 400
    And el mensaje de error debe indicar "Coordenadas inválidas"
