package com.upc.pre.urbanvoiceapp.districts.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DistrictResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("risk_level")
    private Integer riskLevel;

    @JsonProperty("risk_category")
    private String riskCategory;

    @JsonProperty("risk_description")
    private String riskDescription;

    @JsonProperty("boundary")
    private List<BoundaryPointResponse> boundary;

    @JsonProperty("description")
    private String description;

    @JsonProperty("incident_count")
    private Integer incidentCount;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BoundaryPointResponse {
        @JsonProperty("latitude")
        private Double latitude;

        @JsonProperty("longitude")
        private Double longitude;
    }
}
