package com.upc.pre.urbanvoiceapp.notifications.domain.entities;

import com.upc.pre.urbanvoiceapp.notifications.domain.valueobjects.AlertType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entity que representa una notificación/alerta enviada a un usuario.
 */
@Getter
@Setter
public class Alert {
    private final Long id;
    private final Long userId;
    private final AlertType alertType;
    private String title;
    private String message;
    private Double latitude;
    private Double longitude;
    private Boolean isRead;
    private LocalDateTime createdAt;

    public Alert(Long id, Long userId, AlertType alertType, String title, String message, 
                 Double latitude, Double longitude) {
        this.id = id;
        this.userId = userId;
        this.alertType = alertType;
        this.title = title;
        this.message = message;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isRead = false;
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Constructor para crear una nueva alerta sin ID.
     */
    public Alert(Long userId, AlertType alertType, String title, String message,
                 Double latitude, Double longitude) {
        this(null, userId, alertType, title, message, latitude, longitude);
    }

    /**
     * Marca la alerta como leída.
     */
    public void markAsRead() {
        this.isRead = true;
    }

    /**
     * Valida el estado de la alerta.
     */
    public void validate() {
        if (userId == null || alertType == null || title == null || message == null) {
            throw new IllegalStateException("Alert must have userId, alertType, title, and message");
        }
    }
}
