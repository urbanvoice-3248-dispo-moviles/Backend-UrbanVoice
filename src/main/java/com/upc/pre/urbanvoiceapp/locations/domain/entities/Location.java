package com.upc.pre.urbanvoiceapp.locations.domain.entities;

import com.upc.pre.urbanvoiceapp.locations.domain.valueobjects.GeoCoordinate;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Entidad de dominio que representa una ubicación reportada dentro de
 * UrbanVoice.
 *
 * <p>Encapsula sus coordenadas geográficas ({@link GeoCoordinate}), la
 * dirección textual, el distrito al que pertenece y una descripción
 * opcional. La entidad es responsable de mantener su propia consistencia
 * mediante {@link #validate()}.</p>
 */
@Getter
public class Location {
    private final Long id;
    private final GeoCoordinate coordinate;
    private String address;
    private String district;
    private String description;
    private LocalDateTime createdAt;

    public Location(Long id, GeoCoordinate coordinate, String address, String district) {
        this.id = id;
        this.coordinate = coordinate;
        this.address = address;
        this.district = district;
        this.createdAt = LocalDateTime.now();
    }

    public Location(GeoCoordinate coordinate, String address, String district) {
        this(null, coordinate, address, district);
    }

    /**
     * Actualiza la descripción asociada a la ubicación.
     *
     * @param description nueva descripción de la ubicación
     */
    public void updateDescription(String description) {
        this.description = description;
    }

    /**
     * Valida las invariantes de la entidad.
     *
     * @throws IllegalStateException si la ubicación no tiene coordenada
     */
    public void validate() {
        if (coordinate == null) {
            throw new IllegalStateException("Location must have a coordinate");
        }
    }
}
