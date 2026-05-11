package com.upc.pre.urbanvoiceapp.reports.domain.valueobjects;

import java.util.Objects;

/**
 * Value Object que encapsula la ubicación geográfica de un incidente.
 */
public class GeoLocation {
    private final double latitude;
    private final double longitude;
    private final String address;

    public GeoLocation(double latitude, double longitude, String address) {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90");
        }
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180");
        }
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    /**
     * Calcula la distancia entre dos ubicaciones usando la fórmula de Haversine.
     */
    public double distanceTo(GeoLocation other) {
        final int R = 6371; // Radio de la Tierra en kilómetros
        double dLat = Math.toRadians(other.latitude - this.latitude);
        double dLon = Math.toRadians(other.longitude - this.longitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(other.latitude)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoLocation that = (GeoLocation) o;
        return Double.compare(that.latitude, latitude) == 0 && Double.compare(that.longitude, longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public String toString() {
        return "GeoLocation{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", address='" + address + '\'' +
                '}';
    }
}
