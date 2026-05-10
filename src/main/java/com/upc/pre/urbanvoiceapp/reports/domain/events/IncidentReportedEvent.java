package com.upc.pre.urbanvoiceapp.reports.domain.events;

import com.upc.pre.urbanvoiceapp.shared.domain.events.DomainEvent;

public class IncidentReportedEvent extends DomainEvent {
    private final Long reportId;
    private final Long userId;
    private final String type;
    private final String location;
    private final double latitude;
    private final double longitude;

    public IncidentReportedEvent(Long reportId, Long userId, String type, String location, double latitude, double longitude) {
        super();
        this.reportId = reportId;
        this.userId = userId;
        this.type = type;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getReportId() {
        return reportId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String getEventName() {
        return "IncidentReported";
    }
}
