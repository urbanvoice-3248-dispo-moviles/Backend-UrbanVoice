package com.upc.pre.urbanvoiceapp.notifications.domain.entities;

import lombok.Getter;

@Getter
public class UserAlertConfig {
    private final Long id;
    private final Long userId;
    private boolean enabled;
    private double radiusInKm;
    private boolean notifyByEmail;

    public UserAlertConfig(Long id, Long userId, boolean enabled, double radiusInKm, boolean notifyByEmail) {
        this.id = id;
        this.userId = userId;
        this.enabled = enabled;
        this.radiusInKm = radiusInKm;
        this.notifyByEmail = notifyByEmail;
    }

    public UserAlertConfig(Long userId) {
        this(null, userId, true, 5.0, false);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setRadiusInKm(double radiusInKm) {
        if (radiusInKm <= 0) throw new IllegalArgumentException("Radius must be positive");
        this.radiusInKm = radiusInKm;
    }

    public void setNotifyByEmail(boolean notifyByEmail) {
        this.notifyByEmail = notifyByEmail;
    }
}
