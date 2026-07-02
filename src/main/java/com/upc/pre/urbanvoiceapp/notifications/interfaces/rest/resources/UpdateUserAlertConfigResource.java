package com.upc.pre.urbanvoiceapp.notifications.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserAlertConfigResource {
    @JsonProperty("enabled")
    private Boolean enabled;

    @JsonProperty("radius_in_km")
    private Double radiusInKm;

    @JsonProperty("notify_by_email")
    private Boolean notifyByEmail;
}
