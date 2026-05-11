package com.upc.pre.urbanvoiceapp.locations.domain.valueobjects;

import java.util.Objects;

/**
 * Value Object que encapsula el nivel de riesgo en una ubicación.
 */
public class RiskLevel {
    private final int level; // 0-5 (0=seguro, 5=muy peligroso)
    private final String description;

    public RiskLevel(int level, String description) {
        if (level < 0 || level > 5) {
            throw new IllegalArgumentException("Risk level must be between 0 and 5");
        }
        this.level = level;
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }

    public String getRiskCategory() {
        return switch (level) {
            case 0, 1 -> "SEGURO";
            case 2, 3 -> "MODERADO";
            case 4, 5 -> "PELIGROSO";
            default -> "DESCONOCIDO";
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RiskLevel that = (RiskLevel) o;
        return level == that.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(level);
    }

    @Override
    public String toString() {
        return "RiskLevel{" + level + " - " + description + "}";
    }
}
