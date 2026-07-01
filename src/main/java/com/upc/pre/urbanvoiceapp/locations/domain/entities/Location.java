package com.upc.pre.urbanvoiceapp.locations.domain.entities;

import com.upc.pre.urbanvoiceapp.locations.domain.valueobjects.GeoCoordinate;
import lombok.Getter;

import java.time.LocalDateTime;

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

    public void updateDescription(String description) {
        this.description = description;
    }

    public void validate() {
        if (coordinate == null) {
            throw new IllegalStateException("Location must have a coordinate");
        }
    }
}
