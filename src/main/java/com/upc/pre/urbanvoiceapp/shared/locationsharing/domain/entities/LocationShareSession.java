package com.upc.pre.urbanvoiceapp.shared.locationsharing.domain.entities;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LocationShareSession {
    private final Long id;
    private final Long ownerUserId;
    private final Long targetUserId;
    private Boolean active;
    private LocalDateTime createdAt;

    public LocationShareSession(Long id, Long ownerUserId, Long targetUserId) {
        this.id = id;
        this.ownerUserId = ownerUserId;
        this.targetUserId = targetUserId;
        this.active = true;
        this.createdAt = LocalDateTime.now();
    }

    public LocationShareSession(Long ownerUserId, Long targetUserId) {
        this(null, ownerUserId, targetUserId);
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    public void validate() {
        if (ownerUserId == null || targetUserId == null) {
            throw new IllegalStateException("Share session must have both users");
        }
        if (ownerUserId.equals(targetUserId)) {
            throw new IllegalStateException("Cannot share location with yourself");
        }
    }
}
