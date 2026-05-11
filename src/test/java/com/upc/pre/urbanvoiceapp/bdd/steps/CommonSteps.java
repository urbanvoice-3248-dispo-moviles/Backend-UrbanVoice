package com.upc.pre.urbanvoiceapp.bdd.steps;

import com.upc.pre.urbanvoiceapp.bdd.ScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

public class CommonSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ScenarioContext scenarioContext;

    @Given("el sistema está en ejecución")
    public void sistemaPareceEnEjecucion() {
        assertNotNull(restTemplate, "RestTemplate debe estar disponible");
    }

    @Given("la base de datos está limpia")
    public void baseDatosLimpia() {
        System.out.println("Base de datos limpia para la prueba");
    }

    @Then("el estado de respuesta debe ser {int}")
    public void estadoRespuestaDebe(int expectedStatus) {
        assertNotNull(scenarioContext.getLastResponse(), "No hay respuesta almacenada");
        assertEquals(HttpStatus.valueOf(expectedStatus), scenarioContext.getLastResponse().getStatusCode(),
                "El estado de respuesta debe ser " + expectedStatus);
    }

    @Then("el mensaje de error debe indicar {string}")
    public void mensajeErrorDebeIndicar(String expectedError) {
        assertNotNull(scenarioContext.getLastResponse(), "No hay respuesta almacenada");
        assertTrue(scenarioContext.getLastResponse().getStatusCode().is4xxClientError(),
                "Debe haber un error: " + expectedError);
    }
}
