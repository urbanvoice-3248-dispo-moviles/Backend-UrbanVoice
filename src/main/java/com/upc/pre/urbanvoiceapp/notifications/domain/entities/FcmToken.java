package com.upc.pre.urbanvoiceapp.notifications.domain.entities;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FcmToken {
    private final Long id;
    private final Long userId;
    private String token;
    private String deviceType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public FcmToken(Long id, Long userId, String token, String deviceType,
                    LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.deviceType = deviceType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public FcmToken(Long userId, String token, String deviceType) {
        this(null, userId, token, deviceType, LocalDateTime.now(), LocalDateTime.now());
    }

    public void updateToken(String token) {
        this.token = token;
        this.updatedAt = LocalDateTime.now();
    }
}
