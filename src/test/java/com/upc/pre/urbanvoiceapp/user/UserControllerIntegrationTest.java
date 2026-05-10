package com.upc.pre.urbanvoiceapp.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upc.pre.urbanvoiceapp.models.UserProfile;
import com.upc.pre.urbanvoiceapp.schemas.UpdateUserProfileSchema;
import com.upc.pre.urbanvoiceapp.schemas.UserProfileSchema;
import com.upc.pre.urbanvoiceapp.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserService userService;

    @Test
    void whenCreateUser_thenReturnsUser() throws Exception {
        // 🔹 Arrange
        UserProfileSchema newUser = new UserProfileSchema(
                "John", "Doe", "1234", "john@example.com", "pwrd123", "1", null
        );
        // 🔹 Act & Assert
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void whenGetUserByEmail_thenReturnsUser() throws Exception {
        // 🔹 Arrange
        UserProfile user = new UserProfile("Jane", "jane@example.com", "password", "Smith", "999888777", "2", null);
        userService.save(user);

        // 🔹 Act & Assert
        mockMvc.perform(get("/api/v1/users/jane@example.com")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("jane@example.com"));
    }

    @Test
    void whenUpdateUser_thenReturnsUpdatedUser() throws Exception {
        // 🔹 Arrange
        UserProfile saved = userService.save(new UserProfile("Tom", "tom@example.com", "pass", "White", "555444333", "3", null));

        UpdateUserProfileSchema update = new UpdateUserProfileSchema(
                "Tommy", "newlastname", "123456789", null
        );

        // 🔹 Act & Assert
        mockMvc.perform(put("/api/v1/users/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tommy"));
    }

    @Test
    void whenDeleteUser_thenUserIsRemoved() throws Exception {
        // 🔹 Arrange
        UserProfile saved = userService.save(new UserProfile("Delete", "del@example.com", "pass", "User", "000111222", "4", null));

        // 🔹 Act
        mockMvc.perform(delete("/api/v1/users/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted"));

        // 🔹 Assert
        Optional<UserProfile> deleted = userService.findOptionalById(saved.getId());
        assertThat(deleted).isEmpty();
    }
}
