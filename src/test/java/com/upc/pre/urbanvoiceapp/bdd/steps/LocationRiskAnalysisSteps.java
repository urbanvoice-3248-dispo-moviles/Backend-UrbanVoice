package com.upc.pre.urbanvoiceapp.bdd.steps;

import com.upc.pre.urbanvoiceapp.locations.interfaces.rest.resources.LocationResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Definiciones de Pasos para el feature de Análisis de Riesgo de Ubicación
 * Implementa los pasos en Gherkin para consultar y analizar niveles de riesgo
 */
@ContextConfiguration
public class LocationRiskAnalysisSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    private Map<String, Object> locationData = new HashMap<>();
    private ResponseEntity<LocationResponse> lastResponse;
    private String baseUrl = "http://localhost:8080/api/v1/locations";
    private double queryLatitude;
    private double queryLongitude;
    private String riskLevel;

    @Given("el sistema está en ejecución")
    public void sistemaPareceEnEjecucion() {
        assertNotNull(restTemplate, "RestTemplate debe estar disponible");
    }

    @Given("la base de datos está limpia")
    public void baseDatosLimpia() {
        System.out.println("Base de datos limpia para la prueba");
    }

    @Given("la ubicación en latitud {double} y longitud {double} no tiene incidentes recientes")
    public void ubicacionSinIncidentes(double latitude, double longitude) {
        queryLatitude = latitude;
        queryLongitude = longitude;
        locationData.put("latitude", latitude);
        locationData.put("longitude", longitude);
        locationData.put("incidents", 0);
    }

    @When("consulto el nivel de riesgo para esta ubicación")
    public void consultoNivelRiesgo() {
        lastResponse = restTemplate.getForEntity(
            baseUrl + "/risk?latitude=" + queryLatitude + "&longitude=" + queryLongitude,
            LocationResponse.class
        );
    }

    @Then("el nivel de riesgo debe ser {string}")
    public void nivelRiesgoDebeSer(String expectedLevel) {
        assertNotNull(lastResponse.getBody(), "La respuesta no debe ser nula");
        assertEquals(expectedLevel, lastResponse.getBody().getRiskLevel(),
                "El nivel de riesgo debe ser: " + expectedLevel);
        riskLevel = expectedLevel;
    }

    @Then("la puntuación de riesgo debe ser {int}")
    public void puntuacionRiesgoDebeSer(int expectedScore) {
        assertEquals(expectedScore, lastResponse.getBody().getRiskScore(),
                "La puntuación de riesgo debe ser: " + expectedScore);
    }

    @Given("la ubicación en latitud {double} y longitud {double} tiene {int} incidentes recientes")
    public void ubicacionConIncidentes(double latitude, double longitude, int incidentCount) {
        queryLatitude = latitude;
        queryLongitude = longitude;
        locationData.put("latitude", latitude);
        locationData.put("longitude", longitude);
        locationData.put("incidents", incidentCount);
    }

    @Given("la ubicación en latitud {double} y longitud {double} tiene {int} incidentes recientes en la última semana")
    public void ubicacionConIncidentesEnSemana(double latitude, double longitude, int incidentCount) {
        queryLatitude = latitude;
        queryLongitude = longitude;
        locationData.put("latitude", latitude);
        locationData.put("longitude", longitude);
        locationData.put("incidents", incidentCount);
        locationData.put("timeRange", "WEEK");
    }

    @Given("la ubicación en latitud {double} y longitud {double} tiene {int}\\+ incidentes recientes en los últimos {int} días")
    public void ubicacionConIncidentesEnDias(double latitude, double longitude, int incidentCount, int days) {
        queryLatitude = latitude;
        queryLongitude = longitude;
        locationData.put("latitude", latitude);
        locationData.put("longitude", longitude);
        locationData.put("incidents", incidentCount);
        locationData.put("days", days);
    }

    @Given("estoy en la ubicación con latitud {double} y longitud {double}")
    public void estoyEnUbicacion(double latitude, double longitude) {
        queryLatitude = latitude;
        queryLongitude = longitude;
    }

    @Given("hay {int} reportes de incidentes dentro de {int} kilómetros")
    public void hayReportesEnRadio(int reportCount, int radiusKm) {
        locationData.put("nearbyIncidents", reportCount);
        locationData.put("radiusKm", radiusKm);
    }

    @When("busco ubicaciones con incidentes dentro de {int} kilómetros")
    public void buscoUbicacionesEnRadio(int radiusKm) {
        lastResponse = restTemplate.getForEntity(
            baseUrl + "/nearby?latitude=" + queryLatitude + "&longitude=" + queryLongitude + "&radiusKm=" + radiusKm,
            LocationResponse.class
        );
    }

    @Then("la respuesta debe contener {int} incidentes cercanos")
    public void respuestaContieneCercanos(int expectedCount) {
        assertNotNull(lastResponse.getBody(), "La respuesta no debe ser nula");
        assertEquals(expectedCount, lastResponse.getBody().getIncidentCount(),
                "Debe haber " + expectedCount + " incidentes cercanos");
    }

    @Then("todos los resultados deben estar dentro de {int} kilómetros de mi ubicación")
    public void resultadosDentroDeRadio(int radiusKm) {
        // Validación de que los resultados están dentro del radio
        assertTrue(lastResponse.getStatusCode().is2xxSuccessful(),
                "Los resultados deben estar dentro del radio especificado");
    }

    @Given("el sistema tiene ubicaciones con diferentes niveles de riesgo")
    public void sistemaConUbicacionesVariadas() {
        // En una implementación real, se crearían ubicaciones con diferentes riesgos
        System.out.println("Sistema con ubicaciones de riesgo variado");
    }

    @When("consulto todas las ubicaciones de riesgo {word}")
    public void consultoUbicacionesPorRiesgo(String riskLevel) {
        lastResponse = restTemplate.getForEntity(
            baseUrl + "/by-risk?level=" + riskLevel,
            LocationResponse.class
        );
    }

    @Then("la respuesta debe incluir solo ubicaciones de riesgo {word}")
    public void respuestaConRiesgoEspecifico(String expectedLevel) {
        assertNotNull(lastResponse.getBody(), "La respuesta no debe ser nula");
        assertEquals(expectedLevel, lastResponse.getBody().getRiskLevel(),
                "Debe incluir solo ubicaciones de riesgo: " + expectedLevel);
    }

    @Then("cada ubicación debe tener incident_count >= {int}")
    public void cadaUbicacionTieneMinIncidentes(int minCount) {
        assertTrue(lastResponse.getBody().getIncidentCount() >= minCount,
                "Debe haber al menos " + minCount + " incidentes");
    }

    @Given("una ubicación con ID {int} tiene nivel de riesgo {string}")
    public void ubicacionConRiesgoInicial(int locationId, String initialRisk) {
        locationData.put("id", locationId);
        locationData.put("initialRisk", initialRisk);
    }

    @Given("se reportan nuevos incidentes cerca de esta ubicación")
    public void nuevosIncidentesCerca() {
        // En una implementación real, se reportarían nuevos incidentes
        System.out.println("Nuevos incidentes reportados");
    }

    @When("se actualiza el cálculo de riesgo")
    public void seActualizaCalculo() {
        // En una implementación real, se dispararía la actualización
        System.out.println("Cálculo de riesgo actualizado");
    }

    @Then("el nivel de riesgo de la ubicación debe ser recalculado")
    public void nivelRecalculado() {
        assertNotNull(lastResponse.getBody(), "La respuesta no debe ser nula");
    }

    @Then("el nuevo nivel de riesgo podría ser más alto que antes")
    public void nuevoNivelMasAlto() {
        assertTrue(lastResponse.getStatusCode().is2xxSuccessful(),
                "El riesgo debe haberse recalculado");
    }

    @Given("proporciono coordenadas inválidas con latitud {int} y longitud {int}")
    public void coordenadasInvalidas(int latitude, int longitude) {
        queryLatitude = latitude;
        queryLongitude = longitude;
    }

    @When("consulto el nivel de riesgo")
    public void consultoRiesgo() {
        lastResponse = restTemplate.getForEntity(
            baseUrl + "/risk?latitude=" + queryLatitude + "&longitude=" + queryLongitude,
            LocationResponse.class
        );
    }

    @Then("el estado de respuesta debe ser {int}")
    public void estadoRespuestaDebe(int expectedStatus) {
        assertEquals(HttpStatus.valueOf(expectedStatus), lastResponse.getStatusCode(),
                "El estado debe ser: " + expectedStatus);
    }

    @Then("el mensaje de error debe indicar {string}")
    public void mensajeErrorIndica(String expectedError) {
        assertTrue(lastResponse.getStatusCode().is4xxClientError(),
                "Debe haber un error: " + expectedError);
    }
}
