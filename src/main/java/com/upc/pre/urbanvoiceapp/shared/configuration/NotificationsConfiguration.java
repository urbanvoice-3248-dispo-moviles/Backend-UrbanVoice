package com.upc.pre.urbanvoiceapp.shared.configuration;

import com.upc.pre.urbanvoiceapp.notifications.domain.services.AlertDomainService;
import com.upc.pre.urbanvoiceapp.notifications.infrastructure.outboundservices.EmailNotificationService;
import com.upc.pre.urbanvoiceapp.notifications.infrastructure.outboundservices.PushNotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración para el bounded context de Notificaciones.
 */
@Configuration
public class NotificationsConfiguration {

    @Bean
    public AlertDomainService alertDomainService(PushNotificationService pushService, 
                                                  EmailNotificationService emailService) {
        return new AlertDomainServiceImpl(pushService, emailService);
    }

    /**
     * Implementación del servicio de dominio para alertas.
     */
    public static class AlertDomainServiceImpl implements AlertDomainService {
        private final PushNotificationService pushNotificationService;
        private final EmailNotificationService emailNotificationService;

        public AlertDomainServiceImpl(PushNotificationService pushService,
                                     EmailNotificationService emailService) {
            this.pushNotificationService = pushService;
            this.emailNotificationService = emailService;
        }

        @Override
        public com.upc.pre.urbanvoiceapp.notifications.domain.entities.Alert createAlert(
                Long userId, 
                com.upc.pre.urbanvoiceapp.notifications.domain.valueobjects.AlertType alertType, 
                String title, String message,
                Double latitude, Double longitude) {
            return new com.upc.pre.urbanvoiceapp.notifications.domain.entities.Alert(
                    userId, alertType, title, message, latitude, longitude);
        }

        @Override
        public void sendPushNotification(com.upc.pre.urbanvoiceapp.notifications.domain.entities.Alert alert) {
            pushNotificationService.sendNotification(alert.getUserId(), alert.getTitle(), alert.getMessage());
        }

        @Override
        public void sendEmailNotification(com.upc.pre.urbanvoiceapp.notifications.domain.entities.Alert alert, String email) {
            emailNotificationService.sendEmail(email, alert.getTitle(), alert.getMessage());
        }
    }
}
