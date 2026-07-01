package com.upc.pre.urbanvoiceapp.reports.domain.entities;

import com.upc.pre.urbanvoiceapp.reports.domain.events.IncidentReportedEvent;
import com.upc.pre.urbanvoiceapp.reports.domain.valueobjects.GeoLocation;
import com.upc.pre.urbanvoiceapp.reports.domain.valueobjects.IncidentType;
import com.upc.pre.urbanvoiceapp.shared.domain.events.DomainEvent;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Aggregate Root del contexto Report Management (CORE DOMAIN).
 * Representa un reporte de incidente realizado por un ciudadano.
 *
 * <p>Concentra las reglas basicas de consistencia del reporte y mantiene los
 * eventos de dominio que deben publicarse despues de persistir cambios.</p>
 */
@Getter
public class IncidentReport {
    private final Long id;
    private final Long userId;
    private final IncidentType incidentType;
    private final GeoLocation location;
    private String title;
    private String description;
    private String mediaUrl;
    private boolean isAnonymous;
    private LocalDateTime reportedAt;
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    /**
     * Reconstruye un reporte existente desde persistencia.
     *
     * @param id identificador del reporte.
     * @param userId identificador del ciudadano que reporto el incidente.
     * @param incidentType tipo de incidente reportado.
     * @param location ubicacion geografica del incidente.
     * @param title titulo del reporte.
     * @param description descripcion del incidente.
     * @param mediaUrl URL de evidencia multimedia.
     * @param isAnonymous indica si el reporte fue creado como anonimo.
     */
    public IncidentReport(Long id, Long userId, IncidentType incidentType, GeoLocation location,
                          String title, String description, String mediaUrl, boolean isAnonymous) {
        this.id = id;
        this.userId = userId;
        this.incidentType = incidentType;
        this.location = location;
        this.title = title;
        this.description = description;
        this.mediaUrl = mediaUrl;
        this.isAnonymous = isAnonymous;
        this.reportedAt = LocalDateTime.now();
    }

    /**
     * Constructor para crear un nuevo reporte sin ID.
     *
     * @param userId identificador del ciudadano que reporta el incidente.
     * @param incidentType tipo de incidente reportado.
     * @param location ubicacion geografica del incidente.
     * @param title titulo del reporte.
     * @param description descripcion del incidente.
     * @param mediaUrl URL de evidencia multimedia.
     * @param isAnonymous indica si el reporte debe ocultar la identidad del usuario.
     */
    public IncidentReport(Long userId, IncidentType incidentType, GeoLocation location,
                          String title, String description, String mediaUrl, boolean isAnonymous) {
        this(null, userId, incidentType, location, title, description, mediaUrl, isAnonymous);
    }

    /**
     * Actualiza el titulo del reporte.
     *
     * @param title nuevo titulo no vacio.
     * @throws IllegalArgumentException si el titulo es nulo o esta en blanco.
     */
    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank");
        }
        this.title = title;
    }

    /**
     * Actualiza la URL de evidencia multimedia.
     *
     * @param mediaUrl nueva URL multimedia; puede ser {@code null}.
     */
    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    /**
     * Restaura la fecha de reporte cuando el agregado se reconstruye desde persistencia.
     *
     * @param reportedAt fecha y hora en la que se reporto el incidente.
     */
    public void setReportedAt(LocalDateTime reportedAt) {
        this.reportedAt = reportedAt;
    }

    /**
     * Actualiza la descripcion del reporte.
     *
     * @param newDescription nueva descripcion no vacia.
     * @throws IllegalArgumentException si la descripcion es nula o esta en blanco.
     */
    public void updateDescription(String newDescription) {
        if (newDescription == null || newDescription.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank");
        }
        this.description = newDescription;
    }

    /**
     * Registra un evento de creacion cuando se persiste por primera vez.
     *
     * <p>El evento queda encolado en el agregado hasta que la capa de aplicacion
     * lo extrae mediante {@link #pullDomainEvents()}.</p>
     */
    public void recordCreation() {
        this.domainEvents.add(new IncidentReportedEvent(
                this.id,
                this.userId,
                this.incidentType.getType(),
                this.location.getAddress(),
                this.location.getLatitude(),
                this.location.getLongitude()
        ));
    }

    /**
     * Valida el estado del agregado.
     *
     * @throws IllegalStateException si faltan datos obligatorios del reporte.
     */
    public void validate() {
        if (userId == null || incidentType == null || location == null) {
            throw new IllegalStateException("IncidentReport must have userId, incidentType, and location");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalStateException("IncidentReport must have a title");
        }
    }

    /**
     * Obtiene y limpia los eventos registrados.
     *
     * @return eventos pendientes de publicacion en orden de registro.
     */
    public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> events = new ArrayList<>(domainEvents);
        domainEvents.clear();
        return events;
    }
}
