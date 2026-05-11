package com.upc.pre.urbanvoiceapp.notifications.infrastructure.outboundservices;

import org.springframework.stereotype.Service;

/**
 * Servicio para enviar notificaciones push.
 * Implementación simplificada - en producción usaría Firebase Cloud Messaging, OneSignal, etc.
 */
@Service
public class PushNotificationService {

    public void sendNotification(Long userId, String title, String message) {
        // En producción, aquí se integraría con un servicio real de push notifications
        System.out.println("PUSH NOTIFICATION -> Usuario: " + userId);
        System.out.println("Título: " + title);
        System.out.println("Mensaje: " + message);
    }
}
