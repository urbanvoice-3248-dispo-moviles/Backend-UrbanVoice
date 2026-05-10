package com.upc.pre.urbanvoiceapp.locations.domain.events;

import com.upc.pre.urbanvoiceapp.shared.domain.events.DomainEvent;

public class RiskZoneIdentifiedEvent extends DomainEvent {
    private final Long locationId;
    private final double latitude;
    private final double longitude;
    private final int riskLevel;
    private final String description;

    public RiskZoneIdentifiedEvent(Long locationId, double latitude, double longitude, int riskLevel, String description) {
        super();
        this.locationId = locationId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.riskLevel = riskLevel;
        this.description = description;
    }

    public Long getLocationId() {
        return locationId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getRiskLevel() {
        return riskLevel;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String getEventName() {
        return "RiskZoneIdentified";
    }
}
