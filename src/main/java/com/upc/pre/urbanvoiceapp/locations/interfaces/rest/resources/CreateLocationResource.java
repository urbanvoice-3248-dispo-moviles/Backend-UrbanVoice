package com.upc.pre.urbanvoiceapp.locations.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para crear una nueva ubicación.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLocationResource {
    @JsonProperty("latitude")
    private Double latitude;

    @JsonProperty("longitude")
    private Double longitude;

    @JsonProperty("address")
    private String address;

    @JsonProperty("district")
    private String district;

    @JsonProperty("risk_level")
    private Integer riskLevel = 0;
}
