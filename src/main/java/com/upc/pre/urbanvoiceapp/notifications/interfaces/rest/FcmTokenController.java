package com.upc.pre.urbanvoiceapp.notifications.interfaces.rest;

import com.upc.pre.urbanvoiceapp.notifications.domain.entities.FcmToken;
import com.upc.pre.urbanvoiceapp.notifications.domain.repositories.FcmTokenRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/fcm-tokens")
@Tag(name = "FCM Tokens", description = "API para gestionar tokens de Firebase Cloud Messaging")
public class FcmTokenController {

    private final FcmTokenRepository fcmTokenRepository;

    public FcmTokenController(FcmTokenRepository fcmTokenRepository) {
        this.fcmTokenRepository = fcmTokenRepository;
    }

    @PostMapping
    @Operation(summary = "Registrar o actualizar token FCM")
    public ResponseEntity<Map<String, String>> registerToken(
            @RequestHeader("X-User-ID") Long userId,
            @RequestBody Map<String, String> body) {
        String token = body.get("token");
        String deviceType = body.getOrDefault("deviceType", "android");

        if (token == null || token.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Token is required"));
        }

        Optional<FcmToken> existing = fcmTokenRepository.findByToken(token);
        if (existing.isPresent()) {
            existing.get().updateToken(token);
            fcmTokenRepository.save(existing.get());
        } else {
            FcmToken newToken = new FcmToken(userId, token, deviceType);
            fcmTokenRepository.save(newToken);
        }

        return ResponseEntity.ok(Map.of("message", "Token registered successfully"));
    }

    @DeleteMapping
    @Operation(summary = "Eliminar token FCM (logout)")
    public ResponseEntity<Map<String, String>> deleteToken(
            @RequestHeader("X-User-ID") Long userId) {
        fcmTokenRepository.deleteByUserId(userId);
        return ResponseEntity.ok(Map.of("message", "Tokens deleted successfully"));
    }
}
