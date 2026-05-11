package com.upc.pre.urbanvoiceapp.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Test Runner para ejecutar todas las pruebas BDD
 * Ejecuta los archivos .feature con los Step Definitions
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"com.upc.pre.urbanvoiceapp.bdd", "com.upc.pre.urbanvoiceapp.bdd.steps"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber-reports/cucumber.json",
        "junit:target/cucumber-reports/cucumber.xml"
    },
    monochrome = false,
    dryRun = false
)
public class RunCucumberTest {
    // Test runner para Cucumber
}
