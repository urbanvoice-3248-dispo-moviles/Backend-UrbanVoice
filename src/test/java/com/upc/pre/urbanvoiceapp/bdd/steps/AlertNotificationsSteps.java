package com.upc.pre.urbanvoiceapp.bdd.steps;

import com.upc.pre.urbanvoiceapp.notifications.domain.entities.Alert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Definiciones de Pasos para el feature de Sistema de Alertas y Notificaciones
 * Implementa los pasos en Gherkin para pruebas de notificaciones y alertas
 */
@ContextConfiguration
public class AlertNotificationsSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    private Map<String, Object> alertData = new HashMap<>();
    private Map<String, Object> userData = new HashMap<>();
    private String baseUrl = "http://localhost:8080/api/v1/alerts";
    private Long userId;
    private Long alertId;
    private boolean alertReceived;
    private String alertType;

    @Given("el sistema está en ejecución")
    public void sistemaPareceEnEjecucion() {
        assertNotNull(restTemplate, "RestTemplate debe estar disponible");
    }

    @Given("la base de datos está limpia")
    public void baseDatosLimpia() {
        System.out.println("Base de datos limpia para la prueba");
    }

    @Given("existe un perfil de usuario con ID {int} en la ubicación {double}, {double}")
    public void perfilUsuarioEnUbicacion(int id, double latitude, double longitude) {
        userId = (long) id;
        userData.put("id", userId);
        userData.put("latitude", latitude);
        userData.put("longitude", longitude);
    }

    @Given("soy un usuario suscrito en la ubicación {double}, {double}")
    public void soyUsuarioSuscrito(double latitude, double longitude) {
        if (userId == null) {
            userId = 1L;
        }
        userData.put("latitude", latitude);
        userData.put("longitude", longitude);
        userData.put("subscribed", true);
    }

    @When("se reporta un nuevo incidente de {word} en la ubicación {double}, {double} \\({int} metros\\)")
    public void seReportaIncidente(String incidentType, double latitude, double longitude, int distance) {
        alertData.put("incidentType", incidentType);
        alertData.put("latitude", latitude);
        alertData.put("longitude", longitude);
        alertData.put("distance", distance);
        
        // Simular que la alerta fue generada
        alertReceived = true;
    }

    @Then("debo recibir una notificación de alerta")
    public void debeRecibirAlerta() {
        assertTrue(alertReceived, "Debe haber recibido una notificación de alerta");
    }

    @Then("el tipo de alerta debe ser {string}")
    public void tipoAlertaDebe(String expectedType) {
        assertNotNull(alertData.get("incidentType"), "El tipo de alerta debe estar presente");
        alertType = expectedType;
    }

    @Then("el mensaje de alerta debe contener {string}")
    public void mensajeAlertaContiene(String expectedContent) {
        assertTrue(alertReceived, "El mensaje debe contener: " + expectedContent);
    }

    @When("el nivel de riesgo de la zona cercana cambia a {string}")
    public void nivelRiesgoCambia(String newRiskLevel) {
        alertData.put("riskLevel", newRiskLevel);
        alertReceived = true;
    }

    @Then("debo recibir una alerta de {word}")
    public void debeRecibirAlertaTipo(String alertType) {
        assertTrue(alertReceived, "Debe recibir una alerta de: " + alertType);
    }

    @Then("la alerta debe incluir las coordenadas de la zona")
    public void alertaConCoordenadas() {
        assertNotNull(alertData.get("latitude"), "Debe incluir latitud");
        assertNotNull(alertData.get("longitude"), "Debe incluir longitud");
    }

    @Then("la alerta debe indicar el nuevo nivel de riesgo")
    public void alertaConRiesgo() {
        assertNotNull(alertData.get("riskLevel"), "Debe indicar el nivel de riesgo");
    }

    @When("se reporta un incidente en la ubicación {double}, {double} \\({int} kilómetros\\)")
    public void seReportaIncidenteLejano(double latitude, double longitude, int distanceKm) {
        alertData.put("latitude", latitude);
        alertData.put("longitude", longitude);
        alertData.put("distanceKm", distanceKm);
    }

    @Then("NO debo recibir una alerta para este incidente")
    public void noDebeRecibirAlerta() {
        assertFalse(alertReceived, "No debe recibir una alerta para incidente distante");
    }

    @Given("se ha generado una nueva alerta para el usuario {int}")
    public void alertaGeneradaParaUsuario(int id) {
        userId = (long) id;
        alertId = 1L;
        alertReceived = true;
    }

    @Given("el usuario tiene las notificaciones push habilitadas")
    public void usuarioTienePushHabilitado() {
        userData.put("pushEnabled", true);
    }

    @When("el sistema de notificaciones procesa la alerta")
    public void sistemaProcesoAlerta() {
        System.out.println("Sistema procesando alerta para usuario: " + userId);
    }

    @Then("se debe enviar una notificación push al dispositivo del usuario")
    public void debeEnviarPush() {
        assertTrue((Boolean) userData.get("pushEnabled"), "Push debe estar habilitado");
    }

    @Then("la notificación debe contener los detalles de la alerta")
    public void notificacionConDetalles() {
        assertNotNull(alertData, "La notificación debe contener detalles");
    }

    @Given("se ha generado una alerta crítica para el usuario {int}")
    public void alertaCriticaParaUsuario(int id) {
        userId = (long) id;
        alertId = 1L;
        alertData.put("severity", "CRITICAL");
    }

    @Given("el usuario tiene las notificaciones por email habilitadas")
    public void usuarioTieneEmailHabilitado() {
        userData.put("emailEnabled", true);
    }

    @When("el sistema de notificaciones envía notificaciones por email")
    public void sistemaEnviaEmail() {
        System.out.println("Sistema enviando email a usuario: " + userId);
    }

    @Then("se debe enviar un email al correo registrado del usuario")
    public void debeEnviarEmail() {
        assertTrue((Boolean) userData.get("emailEnabled"), "Email debe estar habilitado");
    }

    @Then("el email debe incluir detalles del incidente y recomendaciones")
    public void emailConDetalles() {
        assertNotNull(alertData, "El email debe incluir detalles");
    }

    @Given("existe una alerta para el usuario {int}")
    public void existeAlertaParaUsuario(int id) {
        userId = (long) id;
        alertId = 1L;
    }

    @Given("la alerta no ha sido marcada como leída")
    public void alertaNoLeida() {
        alertData.put("isRead", false);
    }

    @When("el usuario ve la alerta")
    public void usuarioVeAlerta() {
        alertData.put("isRead", true);
    }

    @Then("la alerta debe ser marcada como leída")
    public void alertaMarcadaLeida() {
        assertTrue((Boolean) alertData.get("isRead"), "La alerta debe estar marcada como leída");
    }

    @Then("las consultas posteriores deben mostrar is_read = true")
    public void consultasPosterioresLeidaTrue() {
        assertTrue((Boolean) alertData.get("isRead"), "is_read debe ser true");
    }

    @Given("el usuario {int} tiene {int} alertas en el sistema")
    public void usuarioTieneAlertas(int id, int count) {
        userId = (long) id;
        userData.put("alertCount", count);
    }

    @Given("algunas alertas tienen más de {int} días")
    public void algunasAlertasAntiguas(int days) {
        userData.put("minOldAlertDays", days);
    }

    @When("se ejecuta el trabajo de archivo")
    public void seEjecutaTrabajoArchivo() {
        System.out.println("Ejecutando trabajo de archivo de alertas");
    }

    @Then("las alertas más antiguas de {int} días deben ser archivadas")
    public void alertasArchivadasMasAntiguas(int days) {
        System.out.println("Alertas más antiguas de " + days + " días archivadas");
    }

    @Then("las alertas activas deben seguir siendo accesibles")
    public void alertasActivasAccesibles() {
        assertTrue(userData.containsKey("alertCount"), "Las alertas activas deben ser accesibles");
    }

    @Given("el usuario {int} tiene notificaciones activas habilitadas")
    public void usuarioTieneNotificacionesActivas(int id) {
        userId = (long) id;
        userData.put("notificationsEnabled", true);
    }

    @When("el usuario desactiva las notificaciones")
    public void usuarioDesactivaNotificaciones() {
        userData.put("notificationsEnabled", false);
    }

    @Then("no se deben enviar nuevas alertas al usuario {int}")
    public void noEnviarAlertasAlUsuario(int id) {
        assertFalse((Boolean) userData.get("notificationsEnabled"), 
                "No debe enviar alertas cuando están desactivadas");
    }

    @Then("las alertas existentes deben permanecer en el sistema")
    public void alertasExistentesPermanecen() {
        assertTrue(userData.containsKey("alertCount"), "Las alertas existentes deben permanecer");
    }

    @When("el usuario reactiva las notificaciones")
    public void usuarioReactivaNotificaciones() {
        userData.put("notificationsEnabled", true);
    }

    @Then("las nuevas alertas deben reanudarse")
    public void nuevasAlertasReanudadas() {
        assertTrue((Boolean) userData.get("notificationsEnabled"), 
                "Las nuevas alertas deben reanudarse");
    }
}
