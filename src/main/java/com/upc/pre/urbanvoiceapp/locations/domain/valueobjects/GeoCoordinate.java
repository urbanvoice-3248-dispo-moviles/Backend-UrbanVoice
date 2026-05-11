package com.upc.pre.urbanvoiceapp.locations.domain.valueobjects;

import java.util.Objects;

/**
 * Value Object que encapsula la información de una ubicación geográfica.
 */
public class GeoCoordinate {
    private final double latitude;
    private final double longitude;

    public GeoCoordinate(double latitude, double longitude) {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90");
        }
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180");
        }
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoCoordinate that = (GeoCoordinate) o;
        return Double.compare(that.latitude, latitude) == 0 && Double.compare(that.longitude, longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public String toString() {
        return String.format("GeoCoordinate{%f, %f}", latitude, longitude);
    }
}
