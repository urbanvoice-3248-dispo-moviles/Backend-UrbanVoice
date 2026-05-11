package com.upc.pre.urbanvoiceapp.notifications.infrastructure.outboundservices;

import org.springframework.stereotype.Service;

/**
 * Servicio para enviar notificaciones por email.
 * Implementación simplificada - en producción usaría JavaMailSender, SendGrid, etc.
 */
@Service
public class EmailNotificationService {

    public void sendEmail(String email, String subject, String body) {
        // En producción, aquí se integraría con un servicio real de email
        System.out.println("EMAIL -> Destinatario: " + email);
        System.out.println("Asunto: " + subject);
        System.out.println("Cuerpo: " + body);
    }
}
