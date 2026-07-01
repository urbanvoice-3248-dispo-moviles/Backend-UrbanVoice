package com.upc.pre.urbanvoiceapp.reports.domain.valueobjects;

import java.util.Objects;

/**
 * Value Object que encapsula los detalles de un tipo de incidente.
 *
 * <p>La identidad del tipo esta determinada por el codigo {@code type}; la
 * descripcion funciona como etiqueta legible para usuarios y respuestas.</p>
 */
public class IncidentType {
    private final String type;
    private final String description;

    /** Tipo de incidente para robos. */
    public static final IncidentType ROBBERY = new IncidentType("ROBBERY", "Robo");

    /** Tipo de incidente para asaltos. */
    public static final IncidentType ASSAULT = new IncidentType("ASSAULT", "Asalto");

    /** Tipo de incidente para acoso. */
    public static final IncidentType HARASSMENT = new IncidentType("HARASSMENT", "Acoso");

    /** Tipo de incidente para vandalismo. */
    public static final IncidentType VANDALISM = new IncidentType("VANDALISM", "Vandalismo");

    /** Tipo de incidente para accidentes. */
    public static final IncidentType ACCIDENT = new IncidentType("ACCIDENT", "Accidente");

    /** Tipo generico para incidentes no clasificados. */
    public static final IncidentType OTHER = new IncidentType("OTHER", "Otro");

    /**
     * Crea un tipo de incidente.
     *
     * @param type codigo estable del tipo.
     * @param description descripcion legible del tipo.
     * @throws IllegalArgumentException si el codigo es nulo o esta en blanco.
     */
    public IncidentType(String type, String description) {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Type cannot be null or blank");
        }
        this.type = type;
        this.description = description;
    }

    /** @return codigo estable del tipo de incidente. */
    public String getType() {
        return type;
    }

    /** @return descripcion legible del tipo de incidente. */
    public String getDescription() {
        return description;
    }

    /**
     * Compara tipos por codigo.
     *
     * @param o objeto a comparar.
     * @return {@code true} si ambos tipos tienen el mismo codigo.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IncidentType that = (IncidentType) o;
        return Objects.equals(type, that.type);
    }

    /**
     * Genera hash basado en el codigo del tipo.
     *
     * @return hash del codigo de incidente.
     */
    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    /**
     * Representa el tipo para trazas y depuracion.
     *
     * @return texto con codigo y descripcion.
     */
    @Override
    public String toString() {
        return "IncidentType{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
