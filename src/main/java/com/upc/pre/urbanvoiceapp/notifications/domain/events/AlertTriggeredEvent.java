package com.upc.pre.urbanvoiceapp.notifications.domain.events;

import com.upc.pre.urbanvoiceapp.shared.domain.events.DomainEvent;

public class AlertTriggeredEvent extends DomainEvent {
    private final Long userId;
    private final String message;
    private final String alertType;
    private final double latitude;
    private final double longitude;

    public AlertTriggeredEvent(Long userId, String message, String alertType, double latitude, double longitude) {
        super();
        this.userId = userId;
        this.message = message;
        this.alertType = alertType;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public String getAlertType() {
        return alertType;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String getEventName() {
        return "AlertTriggered";
    }
}
