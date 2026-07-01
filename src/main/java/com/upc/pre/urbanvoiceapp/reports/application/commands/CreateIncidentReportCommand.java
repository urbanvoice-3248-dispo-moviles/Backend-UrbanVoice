package com.upc.pre.urbanvoiceapp.reports.application.commands;

/**
 * Command para crear un nuevo reporte de incidente.
 *
 * <p>Transporta los datos capturados en la capa de interfaces hacia el
 * servicio de aplicacion sin exponer directamente los DTO REST al dominio.</p>
 */
public class CreateIncidentReportCommand {
    private final Long userId;
    private final String incidentType;
    private final String title;
    private final String description;
    private final Double latitude;
    private final Double longitude;
    private final String address;
    private final String mediaUrl;
    private final Boolean isAnonymous;

    /**
     * Crea el comando con los datos necesarios para registrar un incidente.
     *
     * @param userId identificador del ciudadano que reporta el incidente.
     * @param incidentType tipo de incidente reportado.
     * @param title titulo breve del reporte.
     * @param description detalle descriptivo del incidente.
     * @param latitude latitud donde ocurrio el incidente.
     * @param longitude longitud donde ocurrio el incidente.
     * @param address direccion textual asociada a la ubicacion.
     * @param mediaUrl URL de evidencia multimedia adjunta.
     * @param isAnonymous indica si el reporte debe ocultar la identidad del usuario.
     */
    public CreateIncidentReportCommand(Long userId, String incidentType, String title, 
                                      String description, Double latitude, Double longitude,
                                      String address, String mediaUrl, Boolean isAnonymous) {
        this.userId = userId;
        this.incidentType = incidentType;
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.mediaUrl = mediaUrl;
        this.isAnonymous = isAnonymous != null ? isAnonymous : false;
    }

    /** @return identificador del usuario que origina el reporte. */
    public Long getUserId() { return userId; }

    /** @return tipo de incidente solicitado para el reporte. */
    public String getIncidentType() { return incidentType; }

    /** @return titulo del reporte. */
    public String getTitle() { return title; }

    /** @return descripcion del incidente. */
    public String getDescription() { return description; }

    /** @return latitud del incidente. */
    public Double getLatitude() { return latitude; }

    /** @return longitud del incidente. */
    public Double getLongitude() { return longitude; }

    /** @return direccion textual del incidente. */
    public String getAddress() { return address; }

    /** @return URL de evidencia multimedia asociada. */
    public String getMediaUrl() { return mediaUrl; }

    /** @return {@code true} cuando el reporte debe tratarse como anonimo. */
    public Boolean getIsAnonymous() { return isAnonymous; }
}
