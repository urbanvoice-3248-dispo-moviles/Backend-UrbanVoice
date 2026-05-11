package com.upc.pre.urbanvoiceapp.notifications.domain.valueobjects;

import java.util.Objects;

/**
 * Value Object para el tipo de alerta.
 */
public class AlertType {
    private final String type;
    private final String description;

    public static final AlertType INCIDENT_NEARBY = new AlertType("INCIDENT_NEARBY", "Incidente cercano");
    public static final AlertType HIGH_RISK_ZONE = new AlertType("HIGH_RISK_ZONE", "Zona de alto riesgo");
    public static final AlertType EMERGENCY = new AlertType("EMERGENCY", "Emergencia");
    public static final AlertType INFORMATION = new AlertType("INFORMATION", "Información");

    public AlertType(String type, String description) {
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
        AlertType alertType = (AlertType) o;
        return Objects.equals(type, alertType.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return "AlertType{" + type + " - " + description + "}";
    }
}
