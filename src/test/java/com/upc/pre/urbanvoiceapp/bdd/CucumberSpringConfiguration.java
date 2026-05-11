package com.upc.pre.urbanvoiceapp.bdd;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Configuración de Cucumber para integración con Spring Boot
 * Define el contexto de la aplicación para las pruebas BDD
 */
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberSpringConfiguration {
    // Configuración automática del contexto de Spring para Cucumber
}
