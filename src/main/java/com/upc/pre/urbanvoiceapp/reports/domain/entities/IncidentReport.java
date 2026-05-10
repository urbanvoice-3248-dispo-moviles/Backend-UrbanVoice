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
    private int upvotes;
    private LocalDateTime reportedAt;
    private final List<DomainEvent> domainEvents = new ArrayList<>();

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
        this.upvotes = 0;
        this.reportedAt = LocalDateTime.now();
    }

    /**
     * Constructor para crear un nuevo reporte sin ID.
     */
    public IncidentReport(Long userId, IncidentType incidentType, GeoLocation location,
                          String title, String description, String mediaUrl, boolean isAnonymous) {
        this(null, userId, incidentType, location, title, description, mediaUrl, isAnonymous);
    }

    /**
     * Incrementa el contador de upvotes.
     */
    public void addUpvote() {
        this.upvotes++;
    }

    /**
     * Decrementa el contador de upvotes.
     */
    public void removeUpvote() {
        if (this.upvotes > 0) {
            this.upvotes--;
        }
    }

    /**
     * Actualiza la descripción del reporte.
     */
    public void updateDescription(String newDescription) {
        if (newDescription == null || newDescription.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank");
        }
        this.description = newDescription;
    }

    /**
     * Registra un evento de creación cuando se persiste por primera vez.
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
     */
    public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> events = new ArrayList<>(domainEvents);
        domainEvents.clear();
        return events;
    }
}
