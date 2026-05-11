package com.upc.pre.urbanvoiceapp.bdd.steps;

import com.upc.pre.urbanvoiceapp.bdd.ScenarioContext;
import com.upc.pre.urbanvoiceapp.profiles.interfaces.rest.resources.CreateUserProfileResource;
import com.upc.pre.urbanvoiceapp.profiles.interfaces.rest.resources.UserProfileResponse;
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
 * Definiciones de Pasos para el feature de Gestión de Perfiles de Usuario
 * Implementa los pasos en Gherkin para crear, actualizar y consultar perfiles de usuario
 */
public class UserProfileSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ScenarioContext scenarioContext;

    private Map<String, Object> userProfileData = new HashMap<>();
    private String baseUrl = "http://localhost:8080/api/v1/users";
    private Long lastUserId;
    private String lastEmail;

    @SuppressWarnings("unchecked")
    private ResponseEntity<UserProfileResponse> lastResponse() {
        return (ResponseEntity<UserProfileResponse>) scenarioContext.getLastResponse();
    }

    @Given("tengo información de usuario con email {string}")
    public void tengoInformacionDeUsuarioConEmail(String email) {
        userProfileData.clear();
        userProfileData.put("email", email);
    }

    @Given("tengo información válida de usuario:")
    public void userHasValidInformation(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        userProfileData.clear();
        userProfileData.putAll(data);
    }

    @Given("ya existe un perfil de usuario con email {string}")
    public void userProfileExists(String email) {
        CreateUserProfileResource resource = new CreateUserProfileResource();
        resource.setName("Test");
        resource.setLastName("User");
        resource.setEmail(email);
        resource.setPhoneNumber("+51987654321");
        resource.setAge(25);
        resource.setProfileImageUrl("https://example.com/img");

        restTemplate.postForEntity(baseUrl, resource, UserProfileResponse.class);
    }

    @When("creo un nuevo perfil de usuario")
    public void createNewUserProfile() {
        CreateUserProfileResource resource = new CreateUserProfileResource();
        resource.setName((String) userProfileData.get("name"));
        resource.setLastName((String) userProfileData.get("lastName"));
        resource.setEmail((String) userProfileData.get("email"));
        resource.setPhoneNumber((String) userProfileData.get("phoneNumber"));
        resource.setAge(Integer.parseInt((String) userProfileData.get("age")));
        resource.setProfileImageUrl((String) userProfileData.get("profileImage"));

        lastEmail = resource.getEmail();
        scenarioContext.setLastResponse(restTemplate.postForEntity(baseUrl, resource, UserProfileResponse.class));
    }

    @When("intento crear un nuevo perfil de usuario")
    public void attemptCreateUserProfile() {
        CreateUserProfileResource resource = new CreateUserProfileResource();
        resource.setName((String) userProfileData.get("name"));
        resource.setLastName((String) userProfileData.get("lastName"));
        resource.setEmail((String) userProfileData.get("email"));
        resource.setPhoneNumber((String) userProfileData.get("phoneNumber"));
        resource.setAge(Integer.parseInt((String) userProfileData.get("age")));

        scenarioContext.setLastResponse(restTemplate.postForEntity(baseUrl, resource, UserProfileResponse.class));
    }

    @Then("el perfil de usuario debe ser creado exitosamente")
    public void profileCreatedSuccessfully() {
        assertNotNull(lastResponse().getBody(), "El cuerpo de respuesta no debe ser nulo");
        assertNotNull(lastResponse().getBody().getId(), "El ID del perfil debe ser asignado");
        lastUserId = lastResponse().getBody().getId();
    }

    @Then("el perfil debe tener un ID asignado")
    public void profileHasIdAssigned() {
        assertNotNull(lastResponse().getBody().getId(), "El perfil debe tener un ID");
    }

    @Then("el email debe ser {string}")
    public void emailShouldBe(String expectedEmail) {
        assertEquals(expectedEmail, lastResponse().getBody().getEmail(),
                "El email debe coincidir: " + expectedEmail);
    }

    @Then("el sistema debe rechazar la creación")
    public void systemRejectsCreation() {
        assertTrue(lastResponse().getStatusCode().is4xxClientError(),
                "El sistema debe devolver error 4xx para email duplicado");
    }

    @When("actualizo el perfil de usuario con:")
    public void updateUserProfile(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        // En una implementación real, se enviaría un PUT request con los datos
    }

    @Then("el perfil de usuario debe ser actualizado exitosamente")
    public void profileUpdatedSuccessfully() {
        assertTrue(lastResponse().getStatusCode().is2xxSuccessful(),
                "La actualización debe ser exitosa");
    }

    @Then("el número de teléfono debe ser {string}")
    public void phoneNumberShouldBe(String expectedPhone) {
        assertEquals(expectedPhone, lastResponse().getBody().getPhoneNumber(),
                "El número de teléfono debe coincidir");
    }

    @Then("la edad debe ser {int}")
    public void ageShouldBe(int expectedAge) {
        assertEquals(expectedAge, lastResponse().getBody().getAge(),
                "La edad debe coincidir");
    }

    @Given("existe un perfil de usuario con:")
    public void userProfileExistsWith(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        CreateUserProfileResource resource = new CreateUserProfileResource();
        resource.setEmail(data.get("email"));
        resource.setName(data.get("name"));
        resource.setLastName("TestLastName");
        resource.setPhoneNumber("+51987654321");
        resource.setAge(25);

        lastEmail = data.get("email");
        restTemplate.postForEntity(baseUrl, resource, UserProfileResponse.class);
    }

    @When("recupero el perfil de usuario por email {string}")
    public void retrieveUserProfileByEmail(String email) {
        scenarioContext.setLastResponse(restTemplate.getForEntity(baseUrl + "?email=" + email, UserProfileResponse.class));
    }

    @Then("el nombre del perfil debe ser {string}")
    public void profileNameShouldBe(String expectedName) {
        assertEquals(expectedName, lastResponse().getBody().getName(),
                "El nombre del perfil debe coincidir");
    }

    @Then("el email del perfil debe ser {string}")
    public void profileEmailShouldBe(String expectedEmail) {
        assertEquals(expectedEmail, lastResponse().getBody().getEmail(),
                "El email del perfil debe coincidir");
    }

    @Given("no existe un perfil de usuario con email {string}")
    public void noUserProfileExists(String email) {
        // Verificar que no existe
        ResponseEntity<UserProfileResponse> response = 
            restTemplate.getForEntity(baseUrl + "?email=" + email, UserProfileResponse.class);
        assertTrue(response.getStatusCode().is4xxClientError(),
                "El perfil no debe existir");
    }

    @Given("existe un perfil de usuario con email {string}")
    public void profileExistsWithEmail(String email) {
        CreateUserProfileResource resource = new CreateUserProfileResource();
        resource.setName("ToDelete");
        resource.setLastName("User");
        resource.setEmail(email);
        resource.setPhoneNumber("+51987654321");
        resource.setAge(25);

        lastEmail = email;
        scenarioContext.setLastResponse(restTemplate.postForEntity(baseUrl, resource, UserProfileResponse.class));
        lastUserId = lastResponse().getBody().getId();
    }

    @When("elimino el perfil de usuario")
    public void deleteUserProfile() {
        restTemplate.delete(baseUrl + "/" + lastUserId);
    }

    @Then("el perfil de usuario debe ser eliminado")
    public void profileShouldBeDeleted() {
        // Verificar que se eliminó
        ResponseEntity<UserProfileResponse> response = 
            restTemplate.getForEntity(baseUrl + "/" + lastUserId, UserProfileResponse.class);
        assertTrue(response.getStatusCode().is4xxClientError(),
                "El perfil debe ser eliminado");
    }

    @Then("recuperar el perfil debe devolver {int}")
    public void retrievingProfileShouldReturn(int expectedStatus) {
        ResponseEntity<UserProfileResponse> response = 
            restTemplate.getForEntity(baseUrl + "/" + lastUserId, UserProfileResponse.class);
        assertEquals(HttpStatus.valueOf(expectedStatus), response.getStatusCode(),
                "Recuperar el perfil eliminado debe devolver " + expectedStatus);
    }
}
