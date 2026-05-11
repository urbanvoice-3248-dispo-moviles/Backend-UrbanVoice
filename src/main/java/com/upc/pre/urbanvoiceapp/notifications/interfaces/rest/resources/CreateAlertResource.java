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
public class CreateAlertResource {
    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("type")
    private String type;

    @JsonProperty("title")
    private String title;

    @JsonProperty("message")
    private String message;

    @JsonProperty("location")
    private String location;

    @JsonProperty("latitude")
    private Double latitude;

    @JsonProperty("longitude")
    private Double longitude;

    @JsonProperty("description")
    private String description;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("id_report")
    private Long idReport;
}
