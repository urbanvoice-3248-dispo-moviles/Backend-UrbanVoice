package com.upc.pre.urbanvoiceapp.notifications.interfaces.rest;

import com.upc.pre.urbanvoiceapp.notifications.application.services.AlertApplicationService;
import com.upc.pre.urbanvoiceapp.notifications.interfaces.rest.resources.AlertResponse;
import com.upc.pre.urbanvoiceapp.notifications.interfaces.rest.resources.CreateAlertResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/alerts")
@Tag(name = "Alerts", description = "API para gestionar alertas y notificaciones")
public class AlertController {

    private final AlertApplicationService alertService;

    public AlertController(AlertApplicationService alertService) {
        this.alertService = alertService;
    }

    @PostMapping
    @Operation(summary = "Crear nueva alerta")
    public ResponseEntity<AlertResponse> createAlert(@RequestBody CreateAlertResource resource) {
        try {
            var alert = alertService.createAlert(
                    resource.getUserId(),
                    resource.getType(),
                    resource.getTitle(),
                    resource.getMessage(),
                    resource.getLatitude(),
                    resource.getLongitude()
            );
            URI location = URI.create("/api/v1/alerts/" + alert.getId());
            return ResponseEntity.created(location).body(toResponse(alert));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener alerta por ID")
    public ResponseEntity<AlertResponse> getAlertById(@PathVariable Long id) {
        try {
            var alert = alertService.findById(id);
            return ResponseEntity.ok(toResponse(alert));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Obtener alertas por usuario")
    public ResponseEntity<List<AlertResponse>> getAlertsByUserId(@PathVariable Long userId) {
        try {
            var alerts = alertService.findByUserId(userId);
            var responses = alerts.stream().map(this::toResponse).collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    @Operation(summary = "Obtener todas las alertas")
    public ResponseEntity<List<AlertResponse>> getAllAlerts() {
        try {
            var alerts = alertService.findAll();
            var responses = alerts.stream().map(this::toResponse).collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar alerta por ID")
    public ResponseEntity<?> deleteAlertById(@PathVariable Long id) {
        try {
            alertService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping
    @Operation(summary = "Eliminar todas las alertas")
    public ResponseEntity<?> deleteAllAlerts() {
        try {
            alertService.deleteAll();
            return ResponseEntity.ok(java.util.Map.of("message", "All alerts have been deleted"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private AlertResponse toResponse(com.upc.pre.urbanvoiceapp.notifications.domain.entities.Alert alert) {
        AlertResponse response = new AlertResponse();
        response.setId(alert.getId());
        response.setUserId(alert.getUserId());
        response.setType(alert.getAlertType() != null ? alert.getAlertType().getType() : null);
        response.setTitle(alert.getTitle());
        response.setMessage(alert.getMessage());
        response.setLatitude(alert.getLatitude());
        response.setLongitude(alert.getLongitude());
        response.setIsRead(alert.getIsRead() != null && alert.getIsRead());
        response.setCreatedAt(alert.getCreatedAt());
        return response;
    }
}
