package com.upc.pre.urbanvoiceapp.reports.application.queries;

/**
 * Query para obtener reportes cercanos a una ubicacion.
 *
 * <p>Define el punto geografico central y el radio maximo de busqueda usado por
 * la aplicacion para consultar incidentes en una zona.</p>
 */
public class FindNearbyIncidentsQuery {
    private final Double latitude;
    private final Double longitude;
    private final Double radiusInKm;

    /**
     * Crea la query de busqueda geografica.
     *
     * @param latitude latitud del punto central.
     * @param longitude longitud del punto central.
     * @param radiusInKm radio de busqueda expresado en kilometros.
     */
    public FindNearbyIncidentsQuery(Double latitude, Double longitude, Double radiusInKm) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radiusInKm = radiusInKm;
    }

    /** @return latitud del punto central. */
    public Double getLatitude() { return latitude; }

    /** @return longitud del punto central. */
    public Double getLongitude() { return longitude; }

    /** @return radio de busqueda en kilometros. */
    public Double getRadiusInKm() { return radiusInKm; }
}
