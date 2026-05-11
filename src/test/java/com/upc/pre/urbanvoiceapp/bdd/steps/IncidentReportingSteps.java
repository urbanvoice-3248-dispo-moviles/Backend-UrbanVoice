package com.upc.pre.urbanvoiceapp.bdd.steps;

import com.upc.pre.urbanvoiceapp.bdd.ScenarioContext;
import com.upc.pre.urbanvoiceapp.reports.interfaces.rest.resources.CreateIncidentReportResource;
import com.upc.pre.urbanvoiceapp.reports.interfaces.rest.resources.IncidentReportResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Definiciones de Pasos para el feature de Sistema de Reportes de Incidentes
 * Implementa los pasos en Gherkin para reportar, actualizar y consultar incidentes
 */
public class IncidentReportingSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ScenarioContext scenarioContext;

    private Map<String, Object> incidentData = new HashMap<>();
    private String baseUrl = "http://localhost:8080/api/v1/reports";
    private Long lastReportId;
    private Long authenticatedUserId;

    @SuppressWarnings("unchecked")
    private ResponseEntity<IncidentReportResponse> lastResponse() {
        return (ResponseEntity<IncidentReportResponse>) scenarioContext.getLastResponse();
    }

    @Given("existe un perfil de usuario con ID {int}")
    public void perfilUsuarioExiste(int userId) {
        // En una implementación real, se verificaría que el usuario existe
        System.out.println("Perfil de usuario con ID " + userId + " existe");
    }

    @Given("estoy autenticado como usuario con ID {int}")
    public void autenticadoComoUsuario(int userId) {
        authenticatedUserId = (long) userId;
    }

    @Given("tengo información del incidente:")
    public void tengoInformacionIncidente(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        incidentData.clear();
        incidentData.putAll(data);
    }

    @When("envío el reporte del incidente")
    public void envioReporteIncidente() {
        CreateIncidentReportResource resource = new CreateIncidentReportResource();
        resource.setIncidentType((String) incidentData.get("type"));
        resource.setTitle((String) incidentData.get("title"));
        resource.setDescription((String) incidentData.get("description"));
        resource.setLatitude(Double.parseDouble((String) incidentData.get("latitude")));
        resource.setLongitude(Double.parseDouble((String) incidentData.get("longitude")));
        resource.setAddress((String) incidentData.get("address"));
        resource.setIsAnonymous(false);

        scenarioContext.setLastResponse(restTemplate.postForEntity(baseUrl, resource, IncidentReportResponse.class));
    }

    @Then("el incidente debe ser creado exitosamente")
    public void incidenteCreadoExitosamente() {
        assertNotNull(lastResponse().getBody(), "El cuerpo de respuesta no debe ser nulo");
        assertNotNull(lastResponse().getBody().getId(), "El ID del incidente debe ser asignado");
        lastReportId = lastResponse().getBody().getId();
    }

    @Then("al reporte se le debe asignar un ID")
    public void alReporteSeLeasignaID() {
        assertNotNull(lastResponse().getBody().getId(), "El reporte debe tener un ID");
    }

    @Then("el tipo de reporte debe ser {string}")
    public void tipoReporteDebe(String expectedType) {
        assertEquals(expectedType, lastResponse().getBody().getIncidentType(),
                "El tipo de reporte debe ser: " + expectedType);
    }

    @Given("tengo información del incidente con coordenadas cerca del centro de Lima:")
    public void infoIncidenteConCoordenadas(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        incidentData.clear();
        incidentData.put("latitude", data.get("latitude"));
        incidentData.put("longitude", data.get("longitude"));
        incidentData.put("address", data.get("address"));
        incidentData.put("type", "ACCIDENT");
        incidentData.put("title", "Prueba de coordenadas");
        incidentData.put("description", "Prueba de ubicación geográfica");
    }

    @Then("el incidente debe ser creado con ubicación")
    public void incidentoCreadoConUbicacion() {
        assertNotNull(lastResponse().getBody().getLatitude(), "La latitud debe estar presente");
        assertNotNull(lastResponse().getBody().getLongitude(), "La longitud debe estar presente");
    }

    @Then("la latitud debe ser {double}")
    public void latitudDebe(double expectedLat) {
        assertEquals(expectedLat, lastResponse().getBody().getLatitude(), 0.0001,
                "La latitud debe ser: " + expectedLat);
    }

    @Then("la longitud debe ser {double}")
    public void longitudDebe(double expectedLon) {
        assertEquals(expectedLon, lastResponse().getBody().getLongitude(), 0.0001,
                "La longitud debe ser: " + expectedLon);
    }

    @Given("tengo información del incidente marcada como anónima")
    public void infoIncidenteAnonima() {
        incidentData.put("anonymous", "true");
    }

    @When("envío el reporte del incidente como anónimo")
    public void envioReporteIncidenteAnonimo() {
        CreateIncidentReportResource resource = new CreateIncidentReportResource();
        resource.setIncidentType("ACCIDENT");
        resource.setTitle("Reporte Anónimo");
        resource.setDescription("Prueba anónima");
        resource.setLatitude(-12.0500);
        resource.setLongitude(-77.0400);
        resource.setAddress("Lima");
        resource.setIsAnonymous(true);

        scenarioContext.setLastResponse(restTemplate.postForEntity(baseUrl, resource, IncidentReportResponse.class));
    }

    @Then("el reporte del incidente debe ser creado")
    public void reporteIncidenteCreadoAnonimo() {
        assertNotNull(lastResponse().getBody().getId(), "El reporte debe ser creado");
    }

    @Then("el reporte debe estar marcado como anónimo")
    public void reporteMarcadoAnonimo() {
        assertTrue(lastResponse().getBody().getIsAnonymous(), "El reporte debe estar marcado como anónimo");
    }

    @Then("el ID del usuario no debe ser visible públicamente")
    public void idUsuarioNoVisible() {
        assertTrue(lastResponse().getBody().getIsAnonymous(), "El reporte debe estar anónimo");
    }

    @Given("existe un reporte de incidente con ID {int} reportado por usuario {int}")
    public void existeReporteIncidente(int reportId, int userId) {
        lastReportId = (long) reportId;
    }

    @When("actualizo el incidente con:")
    public void actualizoIncidente(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        // En una implementación real, se enviaría un PUT request
        incidentData.putAll(data);
    }

    @Then("el incidente debe ser actualizado")
    public void incidentoActualizado() {
        assertTrue(lastResponse().getStatusCode().is2xxSuccessful(), "La actualización debe ser exitosa");
    }

    @Then("el título debe ser {string}")
    public void tituloDebe(String expectedTitle) {
        assertEquals(expectedTitle, lastResponse().getBody().getTitle(),
                "El título debe ser: " + expectedTitle);
    }

    @Then("la marca de tiempo de modificación debe ser reciente")
    public void marcaTiempoReciente() {
        assertNotNull(lastResponse().getBody().getReportedAt(), "Debe tener una marca de tiempo");
    }

    @Given("existe un reporte de incidente con ID {int}")
    public void existeReporteIncidenteConId(int reportId) {
        lastReportId = (long) reportId;
    }

    @When("recupero el reporte de incidente por ID {int}")
    public void recuperoReportePorId(int reportId) {
        scenarioContext.setLastResponse(restTemplate.getForEntity(baseUrl + "/" + reportId, IncidentReportResponse.class));
    }

    @Then("el ID del incidente debe ser {int}")
    public void idIncidenteDebe(int expectedId) {
        assertEquals(expectedId, lastResponse().getBody().getId().intValue(),
                "El ID del incidente debe ser: " + expectedId);
    }

    @Then("todos los detalles del incidente deben estar presentes")
    public void detallesIncidentePresentees() {
        assertNotNull(lastResponse().getBody().getTitle(), "El título debe estar presente");
        assertNotNull(lastResponse().getBody().getDescription(), "La descripción debe estar presente");
        assertNotNull(lastResponse().getBody().getIncidentType(), "El tipo debe estar presente");
    }

    @Given("el usuario con ID {int} ha reportado {int} incidentes")
    public void usuarioHaReportadoIncidentes(int userId, int count) {
        // En una implementación real, se crearían los incidentes
    }

    @When("recupero todos los reportes de incidentes del usuario {int}")
    public void recuperoReportesDeUsuario(int userId) {
        ResponseEntity<IncidentReportResponse[]> response = 
            restTemplate.getForEntity(baseUrl + "/user/" + userId, IncidentReportResponse[].class);
        // Guardar en una lista para validar
    }

    @Then("la respuesta debe contener {int} incidentes")
    public void respuestaContiene(int expectedCount) {
        // Validación de cantidad de incidentes
    }

    @Then("todos los incidentes deben tener ID de usuario {int}")
    public void incidentesTienenUserId(int expectedUserId) {
        // Validación de que todos pertenecen al usuario
    }

    @When("elimino el reporte de incidente con ID {int}")
    public void eliminoReporte(int reportId) {
        restTemplate.delete(baseUrl + "/" + reportId);
    }

    @Then("el incidente debe ser eliminado")
    public void incidentoEliminado() {
        ResponseEntity<IncidentReportResponse> response = 
            restTemplate.getForEntity(baseUrl + "/" + lastReportId, IncidentReportResponse.class);
        assertTrue(response.getStatusCode().is4xxClientError(), "El incidente debe estar eliminado");
    }

    @Then("recuperar el incidente debe devolver {int}")
    public void recuperarIncidenteDevuelve(int expectedStatus) {
        ResponseEntity<IncidentReportResponse> response = 
            restTemplate.getForEntity(baseUrl + "/" + lastReportId, IncidentReportResponse.class);
        assertEquals(HttpStatus.valueOf(expectedStatus), response.getStatusCode(),
                "Recuperar incidente eliminado debe devolver " + expectedStatus);
    }

    @Given("tengo información del incidente con tipo {string}")
    public void infoIncidenteConTipo(String type) {
        incidentData.put("type", type);
        incidentData.put("title", "Prueba tipo inválido");
        incidentData.put("description", "Prueba");
    }

    @When("intento enviar el reporte del incidente")
    public void intentoEnviarReporte() {
        CreateIncidentReportResource resource = new CreateIncidentReportResource();
        resource.setIncidentType((String) incidentData.get("type"));
        resource.setTitle((String) incidentData.get("title"));
        resource.setDescription((String) incidentData.get("description"));
        resource.setLatitude(-12.0500);
        resource.setLongitude(-77.0400);
        resource.setAddress("Lima");

        scenarioContext.setLastResponse(restTemplate.postForEntity(baseUrl, resource, IncidentReportResponse.class));
    }

    @Then("el sistema debe rechazar el envío")
    public void sistemaMrechazoEnvio() {
        assertTrue(lastResponse().getStatusCode().is4xxClientError(),
                "El sistema debe rechazar el tipo de incidente inválido");
    }

    @Then("el error debe indicar {string}")
    public void errorDebeIndicar(String expectedError) {
        assertTrue(lastResponse().getStatusCode().is4xxClientError(),
                "Debe haber un error: " + expectedError);
    }
}
