package com.upc.pre.urbanvoiceapp.reports.domain.events;

import com.upc.pre.urbanvoiceapp.shared.domain.events.DomainEvent;

/**
 * Evento de dominio emitido cuando un ciudadano registra un nuevo incidente.
 *
 * <p>Permite que otros bounded contexts, como notificaciones, reaccionen al
 * reporte sin acoplarse al agregado {@code IncidentReport}.</p>
 */
public class IncidentReportedEvent extends DomainEvent {
    private final Long reportId;
    private final Long userId;
    private final String type;
    private final String location;
    private final double latitude;
    private final double longitude;

    /**
     * Crea el evento con los datos necesarios para consumidores externos.
     *
     * @param reportId identificador del reporte creado.
     * @param userId identificador del usuario que reporto el incidente.
     * @param type tipo de incidente.
     * @param location direccion textual del incidente.
     * @param latitude latitud del incidente.
     * @param longitude longitud del incidente.
     */
    public IncidentReportedEvent(Long reportId, Long userId, String type, String location, double latitude, double longitude) {
        super();
        this.reportId = reportId;
        this.userId = userId;
        this.type = type;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /** @return identificador del reporte creado. */
    public Long getReportId() {
        return reportId;
    }

    /** @return identificador del usuario que reporto el incidente. */
    public Long getUserId() {
        return userId;
    }

    /** @return tipo de incidente reportado. */
    public String getType() {
        return type;
    }

    /** @return direccion textual asociada al incidente. */
    public String getLocation() {
        return location;
    }

    /** @return latitud del incidente. */
    public double getLatitude() {
        return latitude;
    }

    /** @return longitud del incidente. */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Obtiene el nombre estable del evento para trazabilidad e integracion.
     *
     * @return nombre del evento de dominio.
     */
    @Override
    public String getEventName() {
        return "IncidentReported";
    }
}
