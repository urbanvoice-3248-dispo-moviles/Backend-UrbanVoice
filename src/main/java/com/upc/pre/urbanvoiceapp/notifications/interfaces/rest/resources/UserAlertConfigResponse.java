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
public class UserAlertConfigResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("enabled")
    private boolean enabled;

    @JsonProperty("radius_in_km")
    private double radiusInKm;

    @JsonProperty("notify_by_email")
    private boolean notifyByEmail;
}
