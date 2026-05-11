package com.upc.pre.urbanvoiceapp.reports.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO para responder con la información de un reporte de incidente.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncidentReportResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("incident_type")
    private String incidentType;

    @JsonProperty("latitude")
    private Double latitude;

    @JsonProperty("longitude")
    private Double longitude;

    @JsonProperty("address")
    private String address;

    @JsonProperty("media_url")
    private String mediaUrl;

    @JsonProperty("is_anonymous")
    private Boolean isAnonymous;

    @JsonProperty("reported_at")
    private LocalDateTime reportedAt;
}
