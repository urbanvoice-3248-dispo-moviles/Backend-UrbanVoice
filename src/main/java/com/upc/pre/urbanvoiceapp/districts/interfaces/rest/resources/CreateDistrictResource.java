package com.upc.pre.urbanvoiceapp.districts.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDistrictResource {
    @JsonProperty("name")
    private String name;

    @JsonProperty("risk_level")
    private Integer riskLevel;

    @JsonProperty("risk_description")
    private String riskDescription;

    @JsonProperty("boundary")
    private List<BoundaryPoint> boundary;

    @JsonProperty("description")
    private String description;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BoundaryPoint {
        @JsonProperty("latitude")
        private Double latitude;

        @JsonProperty("longitude")
        private Double longitude;
    }
}
