package com.upc.pre.urbanvoiceapp.reports.domain.valueobjects;

import java.util.Objects;

/**
 * Value Object que encapsula la ubicacion geografica de un incidente.
 *
 * <p>Valida rangos de coordenadas y ofrece calculo de distancia para reglas de
 * cercania dentro del bounded context de reportes.</p>
 */
public class GeoLocation {
    private final double latitude;
    private final double longitude;
    private final String address;

    /**
     * Crea una ubicacion geografica valida.
     *
     * @param latitude latitud entre -90 y 90.
     * @param longitude longitud entre -180 y 180.
     * @param address direccion textual asociada a las coordenadas.
     * @throws IllegalArgumentException si la latitud o longitud estan fuera de rango.
     */
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

    /** @return latitud de la ubicacion. */
    public double getLatitude() {
        return latitude;
    }

    /** @return longitud de la ubicacion. */
    public double getLongitude() {
        return longitude;
    }

    /** @return direccion textual asociada. */
    public String getAddress() {
        return address;
    }

    /**
     * Calcula la distancia entre dos ubicaciones usando la formula de Haversine.
     *
     * @param other ubicacion destino.
     * @return distancia aproximada en kilometros.
     */
    public double distanceTo(GeoLocation other) {
        final int R = 6371; // Radio de la Tierra en kilometros
        double dLat = Math.toRadians(other.latitude - this.latitude);
        double dLon = Math.toRadians(other.longitude - this.longitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(other.latitude)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    /**
     * Compara ubicaciones por coordenadas, no por direccion textual.
     *
     * @param o objeto a comparar.
     * @return {@code true} si ambas ubicaciones tienen la misma latitud y longitud.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoLocation that = (GeoLocation) o;
        return Double.compare(that.latitude, latitude) == 0 && Double.compare(that.longitude, longitude) == 0;
    }

    /**
     * Genera hash basado en coordenadas.
     *
     * @return hash de latitud y longitud.
     */
    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    /**
     * Representa la ubicacion para trazas y depuracion.
     *
     * @return texto con latitud, longitud y direccion.
     */
    @Override
    public String toString() {
        return "GeoLocation{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", address='" + address + '\'' +
                '}';
    }
}
