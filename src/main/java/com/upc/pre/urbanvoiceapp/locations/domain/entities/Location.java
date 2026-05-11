package com.upc.pre.urbanvoiceapp.locations.domain.entities;

import com.upc.pre.urbanvoiceapp.locations.domain.valueobjects.GeoCoordinate;
import com.upc.pre.urbanvoiceapp.locations.domain.valueobjects.RiskLevel;
import com.upc.pre.urbanvoiceapp.shared.domain.events.DomainEvent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Aggregate Root del contexto Location Management.
 * Representa una ubicación o zona con información de riesgo.
 */
@Getter
@Setter
public class Location {
    private final Long id;
    private final GeoCoordinate coordinate;
    private String address;
    private String district;
    private RiskLevel riskLevel;
    private int incidentCount;
    private String description;
    private LocalDateTime lastUpdated;
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public Location(Long id, GeoCoordinate coordinate, String address, String district, RiskLevel riskLevel) {
        this.id = id;
        this.coordinate = coordinate;
        this.address = address;
        this.district = district;
        this.riskLevel = riskLevel;
        this.incidentCount = 0;
        this.lastUpdated = LocalDateTime.now();
    }

    /**
     * Constructor para crear una nueva Location sin ID.
     */
    public Location(GeoCoordinate coordinate, String address, String district, RiskLevel riskLevel) {
        this(null, coordinate, address, district, riskLevel);
    }

    /**
     * Incrementa el contador de incidentes y actualiza el nivel de riesgo.
     */
    public void incrementIncidentCount() {
        this.incidentCount++;
        updateRiskLevel();
        this.lastUpdated = LocalDateTime.now();
    }

    /**
     * Actualiza el nivel de riesgo basado en el contador de incidentes.
     */
    private void updateRiskLevel() {
        int newLevel = Math.min(5, this.incidentCount / 2); // Simplificado
        this.riskLevel = new RiskLevel(newLevel, "Actualizado automáticamente");
    }

    /**
     * Valida el estado del agregado.
     */
    public void validate() {
        if (coordinate == null || address == null || riskLevel == null) {
            throw new IllegalStateException("Location must have coordinate, address, and riskLevel");
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
