package com.upc.pre.urbanvoiceapp.locations.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
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
    @NotNull(message = "Latitude is required")
    @Min(value = -90, message = "Latitude must be between -90 and 90")
    @Max(value = 90, message = "Latitude must be between -90 and 90")
    @JsonProperty("latitude")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    @Min(value = -180, message = "Longitude must be between -180 and 180")
    @Max(value = 180, message = "Longitude must be between -180 and 180")
    @JsonProperty("longitude")
    private Double longitude;

    @NotBlank(message = "Address is required")
    @JsonProperty("address")
    private String address;

    @NotBlank(message = "District is required")
    @JsonProperty("district")
    private String district;

    @NotNull(message = "Risk level is required")
    @Min(value = 0, message = "Risk level must be between 0 and 5")
    @Max(value = 5, message = "Risk level must be between 0 and 5")
    @JsonProperty("risk_level")
    private Integer riskLevel;
}
