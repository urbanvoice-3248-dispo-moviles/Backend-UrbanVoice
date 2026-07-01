package com.upc.pre.urbanvoiceapp.shared.locationsharing.domain.entities;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserLiveLocation {
    private final Long id;
    private final Long userId;
    private Double latitude;
    private Double longitude;
    private LocalDateTime updatedAt;

    public UserLiveLocation(Long id, Long userId, Double latitude, Double longitude) {
        this.id = id;
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.updatedAt = LocalDateTime.now();
    }

    public UserLiveLocation(Long userId, Double latitude, Double longitude) {
        this(null, userId, latitude, longitude);
    }

    public void updateLocation(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.updatedAt = LocalDateTime.now();
    }

    public void validate() {
        if (userId == null) {
            throw new IllegalStateException("UserLiveLocation must have a userId");
        }
        if (latitude == null || longitude == null) {
            throw new IllegalStateException("UserLiveLocation must have coordinates");
        }
    }
}
