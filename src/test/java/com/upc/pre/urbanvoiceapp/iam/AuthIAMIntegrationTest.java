package com.upc.pre.urbanvoiceapp.iam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upc.pre.urbanvoiceapp.security.iam.domain.model.aggregates.User;
import com.upc.pre.urbanvoiceapp.security.iam.infrastructure.persistence.jpa.repositories.UserIAMRepository;
import com.upc.pre.urbanvoiceapp.security.iam.interfaces.rest.resources.ChangePasswordResource;
import com.upc.pre.urbanvoiceapp.security.iam.interfaces.rest.resources.SignInResource;
import com.upc.pre.urbanvoiceapp.security.iam.interfaces.rest.resources.SignUpResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthIAMIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserIAMRepository userRepository;

    @Test
    void whenSignUpWithValidData_thenUserIsPersistedInDatabase() throws Exception {
        // 🔹 ARRANGE
        SignUpResource signUp = new SignUpResource("testuser", "secure123", Collections.singletonList("ROLE_USER"));

        // 🔹 ACT
        mockMvc.perform(post("/api/v1/authentication/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUp)))
                .andExpect(status().isCreated());

        // 🔹 ASSERT
        Optional<User> savedUser = userRepository.findByUsername("testuser");
        assertTrue(savedUser.isPresent(), "El usuario debería haberse guardado en la base");
        assertEquals("testuser", savedUser.get().getUsername());
    }

    @Test
    void whenSignInWithValidCredentials_thenReturnsAuthenticatedUser() throws Exception {
        // 🔹 Arrange - primero crear el usuario
        SignUpResource signUp = new SignUpResource("testuser2", "secure123", Collections.singletonList("ROLE_USER"));
        mockMvc.perform(post("/api/v1/authentication/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUp)))
                .andExpect(status().isCreated());

        // 🔹 Act - luego intentar iniciar sesión
        SignInResource signIn = new SignInResource("testuser2", "secure123");

        mockMvc.perform(post("/api/v1/authentication/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signIn)))
                // 🔹 Assert - verificar que la respuesta es 200 OK
                .andExpect(status().isOk());
    }


    @Test
    void whenSignInWithInvalidCredentials_thenReturnsNotFound() throws Exception {
        // 🔹 Arrange - primero crear el usuario
        SignUpResource signUp = new SignUpResource("testuser3", "secure123", Collections.singletonList("ROLE_USER"));

        mockMvc.perform(post("/api/v1/authentication/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUp)))
                .andExpect(status().isCreated());


        SignInResource invalidSignIn = new SignInResource("testuser3", "wrongpassword");

        // 🔹 Act & Assert
        mockMvc.perform(post("/api/v1/authentication/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidSignIn)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenChangePasswordWithValidData_thenPasswordIsUpdated() throws Exception {
        // 🔹 Arrange
        SignUpResource signUp = new SignUpResource("changepassuser", "oldpass123", Collections.singletonList("ROLE_USER"));
        mockMvc.perform(post("/api/v1/authentication/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUp)))
                .andExpect(status().isCreated());

        ChangePasswordResource changePassword = new ChangePasswordResource("changepassuser", "newpass456");

        // 🔹 Act
        var response = mockMvc.perform(put("/api/v1/authentication/change-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changePassword)));

        // 🔹 Assert
        response.andExpect(status().isAccepted());
    }

}
