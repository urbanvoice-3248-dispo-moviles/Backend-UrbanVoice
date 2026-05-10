package com.upc.pre.urbanvoiceapp.controllers;

import com.upc.pre.urbanvoiceapp.models.Alert;
import com.upc.pre.urbanvoiceapp.schemas.AlertSchema;
import com.upc.pre.urbanvoiceapp.services.AlertService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/alerts")
@Tag(name = "Alerts", description = "Alerts Endpoints")
public class AlertController {
    private final AlertService service;

    public AlertController(AlertService service) {
        this.service = service;
    }
    @PostMapping("/")
    public ResponseEntity<?> newAlert(@RequestBody AlertSchema alert) {
        try {
            // Log the incoming alertSchema for debugging
            System.out.println("Received alert: " + alert);

            Alert newAlert = service.saveAlert(alert);
            URI location = URI.create(String.format("/api/v1/alerts/%d", newAlert.getId()));
            return ResponseEntity.created(location).body(newAlert);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getAlertById(@PathVariable Long id) {
        try {
            Alert alert = service.findById(id);
            if (alert == null) {
                return ResponseEntity.badRequest().body(Map.of("message", "Alert not found"));
            }
            return ResponseEntity.ok(alert);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAlertsByUserId(@PathVariable int userId) {
        try {
            List<Alert> alerts = service.findByUserId(userId);
            return ResponseEntity.ok(alerts);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllAlerts() {
        try {
            List<Alert> alerts = service.findAll();
            return alerts.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(alerts);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
    @DeleteMapping("/")
    public ResponseEntity<?> deleteAllAlerts() {
        try {
            service.deleteAllAlerts();
            return ResponseEntity.ok(Map.of("message", "All alerts have been deleted"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
