package com.upc.pre.urbanvoiceapp.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upc.pre.urbanvoiceapp.models.Report;
import com.upc.pre.urbanvoiceapp.schemas.ReportSchema;
import com.upc.pre.urbanvoiceapp.services.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ReportControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ReportService reportService;

    @Test
    void whenCreateReport_thenReturnsCreatedReport() throws Exception {
        // 🔹 Arrange
        ReportSchema report = new ReportSchema(
                "Noise Complaint",
                "There's loud construction noise every morning.",
                "Noise",
                1L,
                null,
                "123 Main St"
        );

        // 🔹 Act & Assert
        mockMvc.perform(post("/api/v1/reports/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(report)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Noise Complaint"))
                .andExpect(jsonPath("$.type").value("Noise"));
    }

    @Test
    void whenGetAllReports_thenReturnsList() throws Exception {
        // 🔹 Arrange
        Report report = new Report("Test Report", "detail", "General", 2L, null, "Avenida Lima");
        reportService.save(report);

        // 🔹 Act & Assert
        mockMvc.perform(get("/api/v1/reports/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void whenGetReportById_thenReturnsReport() throws Exception {
        // 🔹 Arrange
        Report report = reportService.save(new Report("Report By ID", "detail", "Alert", 3L, null, "Calle 9"));

        // 🔹 Act & Assert
        mockMvc.perform(get("/api/v1/reports/" + report.getId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Report By ID"));
    }

    @Test
    void whenGetReportsByUserId_thenReturnsList() throws Exception {
        // 🔹 Arrange
        Long userId = 999L;
        reportService.save(new Report("User Report 1", "detail", "Incident", userId, null, "Z1"));
        reportService.save(new Report("User Report 2", "detail", "Incident", userId, null, "Z2"));

        // 🔹 Act & Assert
        mockMvc.perform(get("/api/v1/reports/user/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void whenDeleteReport_thenReportIsDeleted() throws Exception {
        // 🔹 Arrange
        Report report = reportService.save(new Report("To Delete", "detail", "Trash", 10L, null, "Old Street"));

        // 🔹 Act
        mockMvc.perform(delete("/api/v1/reports/" + report.getId()))
                .andExpect(status().isNoContent());

        // 🔹 Assert
        List<Report> allReports = reportService.findAll();
        assertThat(allReports).noneMatch(r -> r.getId().equals(report.getId()));
    }
}
