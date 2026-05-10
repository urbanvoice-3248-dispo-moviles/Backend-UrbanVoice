package com.upc.pre.urbanvoiceapp.reports.domain.valueobjects;

import java.util.Objects;

/**
 * Value Object que encapsula los detalles de un tipo de incidente.
 */
public class IncidentType {
    private final String type;
    private final String description;

    public static final IncidentType ROBBERY = new IncidentType("ROBBERY", "Robo");
    public static final IncidentType ASSAULT = new IncidentType("ASSAULT", "Asalto");
    public static final IncidentType HARASSMENT = new IncidentType("HARASSMENT", "Acoso");
    public static final IncidentType VANDALISM = new IncidentType("VANDALISM", "Vandalismo");
    public static final IncidentType ACCIDENT = new IncidentType("ACCIDENT", "Accidente");
    public static final IncidentType OTHER = new IncidentType("OTHER", "Otro");

    public IncidentType(String type, String description) {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Type cannot be null or blank");
        }
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IncidentType that = (IncidentType) o;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return "IncidentType{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
