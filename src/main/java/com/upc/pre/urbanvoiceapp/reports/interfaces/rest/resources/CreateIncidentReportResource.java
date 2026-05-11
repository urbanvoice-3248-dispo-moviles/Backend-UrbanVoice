package com.upc.pre.urbanvoiceapp.reports.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para crear un nuevo reporte de incidente.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateIncidentReportResource {
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
    private Boolean isAnonymous = false;
}
