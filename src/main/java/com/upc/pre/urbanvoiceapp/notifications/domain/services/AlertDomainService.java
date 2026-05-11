package com.upc.pre.urbanvoiceapp.notifications.domain.services;

import com.upc.pre.urbanvoiceapp.notifications.domain.entities.Alert;
import com.upc.pre.urbanvoiceapp.notifications.domain.valueobjects.AlertType;

/**
 * Domain Service para crear y gestionar alertas.
 */
public interface AlertDomainService {
    
    /**
     * Crea una alerta para un usuario.
     */
    Alert createAlert(Long userId, AlertType alertType, String title, String message,
                     Double latitude, Double longitude);

    /**
     * Envía una notificación push (puerta de salida).
     */
    void sendPushNotification(Alert alert);

    /**
     * Envía una notificación por email (puerta de salida).
     */
    void sendEmailNotification(Alert alert, String email);
}
