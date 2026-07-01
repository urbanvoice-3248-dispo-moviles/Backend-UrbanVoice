package com.upc.pre.urbanvoiceapp.reports.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO para responder con la informacion de un reporte de incidente.
 *
 * <p>Expone al cliente HTTP los datos persistidos del reporte sin filtrar
 * objetos de dominio fuera del bounded context.</p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncidentReportResponse {
    /** Identificador del reporte. */
    @JsonProperty("id")
    private Long id;

    /** Identificador del usuario que creo el reporte. */
    @JsonProperty("user_id")
    private Long userId;

    /** Titulo breve del incidente. */
    @JsonProperty("title")
    private String title;

    /** Descripcion detallada del incidente. */
    @JsonProperty("description")
    private String description;

    /** Codigo del tipo de incidente. */
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

    /** Indica si el reporte fue creado como anonimo. */
    @JsonProperty("is_anonymous")
    private Boolean isAnonymous;

    /** Fecha y hora en que se registro el reporte. */
    @JsonProperty("reported_at")
    private LocalDateTime reportedAt;
}
