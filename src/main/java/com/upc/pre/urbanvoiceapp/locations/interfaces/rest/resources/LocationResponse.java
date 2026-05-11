package com.upc.pre.urbanvoiceapp.locations.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO para responder con la información de una ubicación.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("latitude")
    private Double latitude;

    @JsonProperty("longitude")
    private Double longitude;

    @JsonProperty("address")
    private String address;

    @JsonProperty("district")
    private String district;

    @JsonProperty("risk_level")
    private Integer riskLevel;

    @JsonProperty("risk_category")
    private String riskCategory;

    @JsonProperty("incident_count")
    private Integer incidentCount;

    @JsonProperty("description")
    private String description;

    @JsonProperty("last_updated")
    private LocalDateTime lastUpdated;
}
