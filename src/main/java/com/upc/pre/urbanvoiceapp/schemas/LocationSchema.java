package com.upc.pre.urbanvoiceapp.schemas;

public record LocationSchema(String latitude, String longitude, Long idReport) {
    public LocationSchema {
        if (latitude == null || latitude.isBlank()) {
            throw new IllegalArgumentException("Latitude cannot be null or empty");
        }
        if (longitude == null || longitude.isBlank()) {
            throw new IllegalArgumentException("Longitude cannot be null or empty");
        }
        if (idReport == null || idReport <= 0) {
            throw new IllegalArgumentException("IdReport cannot be null or empty");
        }
    }
}
