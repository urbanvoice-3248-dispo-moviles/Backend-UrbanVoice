package com.upc.pre.urbanvoiceapp.reports.application.queries;

/**
 * Query para obtener reportes cercanos a una ubicación.
 */
public class FindNearbyIncidentsQuery {
    private final Double latitude;
    private final Double longitude;
    private final Double radiusInKm;

    public FindNearbyIncidentsQuery(Double latitude, Double longitude, Double radiusInKm) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radiusInKm = radiusInKm;
    }

    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public Double getRadiusInKm() { return radiusInKm; }
}
