package com.upc.pre.urbanvoiceapp.notifications.domain.valueobjects;

import java.util.Objects;

/**
 * Value Object que representa el tipo de una alerta.
 *
 * <p>Se compara por su {@code type}, por lo que dos instancias con el mismo
 * código se consideran equivalentes. Ofrece un conjunto de tipos
 * predefinidos como constantes.</p>
 */
public class AlertType {
    private final String type;
    private final String description;

    /** Alerta por un incidente reportado en las cercanías del usuario. */
    public static final AlertType INCIDENT_NEARBY = new AlertType("INCIDENT_NEARBY", "Incidente cercano");
    /** Alerta al ingresar o permanecer en una zona de alto riesgo. */
    public static final AlertType HIGH_RISK_ZONE = new AlertType("HIGH_RISK_ZONE", "Zona de alto riesgo");
    /** Alerta de emergencia con máxima prioridad. */
    public static final AlertType EMERGENCY = new AlertType("EMERGENCY", "Emergencia");
    /** Alerta puramente informativa, sin acción requerida. */
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
