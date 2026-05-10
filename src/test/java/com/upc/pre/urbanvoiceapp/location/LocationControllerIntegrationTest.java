package com.upc.pre.urbanvoiceapp.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upc.pre.urbanvoiceapp.schemas.LocationSchema;
import com.upc.pre.urbanvoiceapp.services.LocationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LocationControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private LocationService locationService;

    @Test
    void whenCreateLocation_thenReturnsCreated() throws Exception {
        // 🔹 Arrange
        LocationSchema location = new LocationSchema(
                "-12.0464", "-77.0428", 1L
        );

        // 🔹 Act & Assert
        mockMvc.perform(post("/api/v1/locations/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(location)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.alatitude").value("-12.0464"))
                .andExpect(jsonPath("$.alongitude").value("-77.0428"));
    }

    @Test
    void whenGetAllLocations_thenReturnsList() throws Exception {
        // 🔹 Arrange
        // 🔹 Act & Assert
        mockMvc.perform(get("/api/v1/locations/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void whenGetDangerousLocations_thenReturnsFilteredList() throws Exception {
        // 🔹 Arrange
        int quantityReports = 1;

        // 🔹 Act & Assert
        mockMvc.perform(get("/api/v1/locations/dangerous")
                        .param("quantity_reports", String.valueOf(quantityReports)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
