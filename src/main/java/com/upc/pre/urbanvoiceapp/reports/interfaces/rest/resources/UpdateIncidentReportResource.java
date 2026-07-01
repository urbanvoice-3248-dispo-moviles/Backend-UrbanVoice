package com.upc.pre.urbanvoiceapp.reports.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para actualizar un reporte de incidente.
 *
 * <p>Representa el cuerpo JSON recibido por {@code PUT /api/v1/reports/{id}}.
 * Los campos nulos indican que el valor existente debe conservarse.</p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateIncidentReportResource {
    /** Nuevo titulo del reporte. */
    @JsonProperty("title")
    private String title;

    /** Nueva descripcion del reporte. */
    @JsonProperty("description")
    private String description;

    /** Nueva URL de evidencia multimedia del reporte. */
    @JsonProperty("media_url")
    private String mediaUrl;
}
