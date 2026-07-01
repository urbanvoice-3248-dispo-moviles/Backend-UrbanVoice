package com.upc.pre.urbanvoiceapp.districts.domain.entities;

import com.upc.pre.urbanvoiceapp.districts.domain.valueobjects.GeoPoint;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class District {
    private final Long id;
    private String name;
    private int riskLevel;
    private String riskDescription;
    private List<GeoPoint> boundary;
    private String description;
    private int incidentCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public District(Long id, String name, int riskLevel, String riskDescription, List<GeoPoint> boundary) {
        this.id = id;
        this.name = name;
        this.riskLevel = riskLevel;
        this.riskDescription = riskDescription;
        this.boundary = boundary;
        this.incidentCount = 0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public District(String name, int riskLevel, String riskDescription, List<GeoPoint> boundary) {
        this(null, name, riskLevel, riskDescription, boundary);
    }

    public District(Long id, String name, int riskLevel, String riskDescription, List<GeoPoint> boundary, int incidentCount, String description) {
        this.id = id;
        this.name = name;
        this.riskLevel = riskLevel;
        this.riskDescription = riskDescription;
        this.boundary = boundary;
        this.incidentCount = incidentCount;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateRiskLevel(int newLevel, String newDescription) {
        if (newLevel < 0 || newLevel > 5) {
            throw new IllegalArgumentException("Risk level must be between 0 and 5");
        }
        this.riskLevel = newLevel;
        this.riskDescription = newDescription;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public void incrementIncidentCount() {
        this.incidentCount++;
        this.updatedAt = LocalDateTime.now();
    }

    public void validate() {
        if (name == null || name.isBlank()) {
            throw new IllegalStateException("District must have a name");
        }
        if (riskLevel < 0 || riskLevel > 5) {
            throw new IllegalStateException("Risk level must be between 0 and 5");
        }
    }

    public String getRiskCategory() {
        return switch (riskLevel) {
            case 0, 1 -> "SEGURO";
            case 2, 3 -> "MODERADO";
            case 4, 5 -> "PELIGROSO";
            default -> "DESCONOCIDO";
        };
    }
}
