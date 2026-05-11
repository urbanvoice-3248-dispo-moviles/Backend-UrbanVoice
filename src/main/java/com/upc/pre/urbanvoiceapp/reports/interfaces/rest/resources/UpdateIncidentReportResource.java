package com.upc.pre.urbanvoiceapp.reports.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para actualizar un reporte de incidente.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateIncidentReportResource {
    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("media_url")
    private String mediaUrl;
}
