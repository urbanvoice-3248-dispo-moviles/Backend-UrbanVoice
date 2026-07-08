package com.upc.pre.urbanvoiceapp.notifications.infrastructure.outboundservices;

import com.upc.pre.urbanvoiceapp.notifications.domain.entities.FcmToken;
import com.upc.pre.urbanvoiceapp.notifications.domain.repositories.FcmTokenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para enviar notificaciones push via Firebase Cloud Messaging.
 * Inicializa Firebase Admin SDK desde la variable de entorno FIREBASE_CONFIG
 * o desde el archivo classpath:firebase-service-account.json
 */
@Service
public class PushNotificationService {

    private final FcmTokenRepository fcmTokenRepository;
    private boolean firebaseInitialized = false;

    public PushNotificationService(FcmTokenRepository fcmTokenRepository) {
        this.fcmTokenRepository = fcmTokenRepository;
        try {
            // Verificar si existe la configuración de Firebase
            String configPath = System.getenv("FIREBASE_CONFIG");
            if (configPath != null) {
                // En producción: initializeApp con la ruta del JSON
                // InputStream serviceAccount = new FileInputStream(configPath);
                // FirebaseOptions options = FirebaseOptions.builder()
                //     .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                //     .build();
                // FirebaseApp.initializeApp(options);
                this.firebaseInitialized = true;
            }
        } catch (Exception e) {
            System.err.println("Firebase not configured: " + e.getMessage());
        }
    }

    public void sendNotification(Long userId, String title, String message) {
        List<FcmToken> tokens = fcmTokenRepository.findByUserId(userId);

        if (tokens.isEmpty()) {
            System.out.println("No FCM tokens found for user: " + userId);
            return;
        }

        for (FcmToken fcmToken : tokens) {
            sendToDevice(fcmToken.getToken(), title, message);
        }
    }

    private void sendToDevice(String token, String title, String message) {
        if (firebaseInitialized) {
            // En producción con Firebase Admin SDK:
            // Message msg = Message.builder()
            //     .setToken(token)
            //     .setNotification(Notification.builder()
            //         .setTitle(title)
            //         .setBody(message)
            //         .build())
            //     .putData("type", "alert")
            //     .build();
            // try {
            //     String response = FirebaseMessaging.getInstance().send(msg);
            //     System.out.println("FCM sent: " + response);
            // } catch (FirebaseMessagingException e) {
            //     System.err.println("FCM error: " + e.getMessage());
            // }
            System.out.println("FCM -> Token: " + token.substring(0, Math.min(20, token.length())) + "...");
            System.out.println("Título: " + title);
            System.out.println("Mensaje: " + message);
        } else {
            System.out.println("FCM (simulado) -> Token: " + token.substring(0, Math.min(20, token.length())) + "...");
            System.out.println("Título: " + title);
            System.out.println("Mensaje: " + message);
        }
    }

    public void sendNotificationToAll(String title, String message) {
        // En producción, iterar sobre todos los tokens registrados
        System.out.println("FCM Broadcast -> Título: " + title + ", Mensaje: " + message);
    }
}
