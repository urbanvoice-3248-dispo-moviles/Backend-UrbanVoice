package com.upc.pre.urbanvoiceapp.notifications.interfaces.rest;

import com.upc.pre.urbanvoiceapp.notifications.application.services.UserAlertConfigApplicationService;
import com.upc.pre.urbanvoiceapp.notifications.interfaces.rest.resources.UpdateUserAlertConfigResource;
import com.upc.pre.urbanvoiceapp.notifications.interfaces.rest.resources.UserAlertConfigResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/alert-config")
@Tag(name = "Alert Config", description = "API para gestionar preferencias de notificaciones")
public class UserAlertConfigController {

    private final UserAlertConfigApplicationService configService;

    public UserAlertConfigController(UserAlertConfigApplicationService configService) {
        this.configService = configService;
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Obtener configuración de alertas del usuario")
    public ResponseEntity<UserAlertConfigResponse> getConfig(@PathVariable Long userId) {
        var config = configService.getOrCreateConfig(userId);
        return ResponseEntity.ok(toResponse(config));
    }

    @PutMapping("/user/{userId}")
    @Operation(summary = "Actualizar configuración de alertas")
    public ResponseEntity<UserAlertConfigResponse> updateConfig(
            @PathVariable Long userId,
            @RequestBody UpdateUserAlertConfigResource resource) {
        var config = configService.updateConfig(userId, resource.getEnabled(),
                resource.getRadiusInKm(), resource.getNotifyByEmail());
        return ResponseEntity.ok(toResponse(config));
    }

    @DeleteMapping("/user/{userId}")
    @Operation(summary = "Eliminar configuración de alertas")
    public ResponseEntity<Void> deleteConfig(@PathVariable Long userId) {
        configService.deleteConfig(userId);
        return ResponseEntity.noContent().build();
    }

    private UserAlertConfigResponse toResponse(com.upc.pre.urbanvoiceapp.notifications.domain.entities.UserAlertConfig config) {
        UserAlertConfigResponse response = new UserAlertConfigResponse();
        response.setId(config.getId());
        response.setUserId(config.getUserId());
        response.setEnabled(config.isEnabled());
        response.setRadiusInKm(config.getRadiusInKm());
        response.setNotifyByEmail(config.isNotifyByEmail());
        return response;
    }
}
