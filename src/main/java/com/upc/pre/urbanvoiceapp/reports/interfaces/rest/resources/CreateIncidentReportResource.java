package com.upc.pre.urbanvoiceapp.reports.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para crear un nuevo reporte de incidente.
 *
 * <p>Representa el cuerpo JSON recibido por {@code POST /api/v1/reports}.</p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateIncidentReportResource {
    /** Titulo breve del incidente. */
    @JsonProperty("title")
    private String title;

    /** Descripcion detallada del incidente. */
    @JsonProperty("description")
    private String description;

    /** Codigo del tipo de incidente reportado. */
    @JsonProperty("incident_type")
    private String incidentType;

    /** Latitud donde ocurrio el incidente. */
    @JsonProperty("latitude")
    private Double latitude;

    /** Longitud donde ocurrio el incidente. */
    @JsonProperty("longitude")
    private Double longitude;

    /** Direccion textual asociada a la ubicacion. */
    @JsonProperty("address")
    private String address;

    /** URL de evidencia multimedia del reporte. */
    @JsonProperty("media_url")
    private String mediaUrl;

    /** Indica si el reporte debe registrarse como anonimo. */
    @JsonProperty("is_anonymous")
    private Boolean isAnonymous = false;
}
