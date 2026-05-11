package com.upc.pre.urbanvoiceapp.locations.interfaces.rest;

import com.upc.pre.urbanvoiceapp.locations.application.services.LocationApplicationService;
import com.upc.pre.urbanvoiceapp.locations.interfaces.rest.resources.CreateLocationResource;
import com.upc.pre.urbanvoiceapp.locations.interfaces.rest.resources.LocationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller para gestionar ubicaciones y zonas de riesgo.
 * Endpoints: /api/v1/locations
 */
@RestController
@RequestMapping("/api/v1/locations")
@Tag(name = "Locations", description = "API para gestionar ubicaciones y zonas de riesgo")
public class LocationController {

    private final LocationApplicationService locationService;

    public LocationController(LocationApplicationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    @Operation(summary = "Crear nueva ubicación")
    public ResponseEntity<LocationResponse> createLocation(@RequestBody CreateLocationResource resource) {
        try {
            var location = locationService.createLocation(
                    resource.getLatitude(),
                    resource.getLongitude(),
                    resource.getAddress(),
                    resource.getDistrict(),
                    resource.getRiskLevel()
            );
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(toResponse(location));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener ubicación por ID")
    public ResponseEntity<LocationResponse> getLocationById(@PathVariable Long id) {
        try {
            var location = locationService.getLocationById(id);
            return location.map(l -> ResponseEntity.ok(toResponse(l)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    @Operation(summary = "Obtener todas las ubicaciones")
    public ResponseEntity<List<LocationResponse>> getAllLocations() {
        try {
            var locations = locationService.getAllLocations();
            var responses = locations.stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/nearby")
    @Operation(summary = "Obtener ubicaciones cercanas")
    public ResponseEntity<List<LocationResponse>> getNearbyLocations(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam(defaultValue = "5.0") Double radiusInKm) {
        try {
            var locations = locationService.getNearbyLocations(latitude, longitude, radiusInKm);
            var responses = locations.stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/district/{district}")
    @Operation(summary = "Obtener ubicaciones por distrito")
    public ResponseEntity<List<LocationResponse>> getLocationsByDistrict(@PathVariable String district) {
        try {
            var locations = locationService.getLocationsByDistrict(district);
            var responses = locations.stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/dangerous")
    @Operation(summary = "Obtener ubicaciones peligrosas")
    public ResponseEntity<List<LocationResponse>> getDangerousLocations(
            @RequestParam(defaultValue = "3") Integer minRiskLevel) {
        try {
            var locations = locationService.getDangerousLocations(minRiskLevel);
            var responses = locations.stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar ubicación")
    public ResponseEntity<?> deleteLocation(@PathVariable Long id) {
        try {
            locationService.deleteLocation(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private LocationResponse toResponse(com.upc.pre.urbanvoiceapp.locations.domain.entities.Location location) {
        LocationResponse response = new LocationResponse();
        response.setId(location.getId());
        response.setLatitude(location.getCoordinate().getLatitude());
        response.setLongitude(location.getCoordinate().getLongitude());
        response.setAddress(location.getAddress());
        response.setDistrict(location.getDistrict());
        response.setRiskLevel(location.getRiskLevel().getLevel());
        response.setRiskCategory(location.getRiskLevel().getRiskCategory());
        response.setIncidentCount(location.getIncidentCount());
        response.setDescription(location.getDescription());
        response.setLastUpdated(location.getLastUpdated());
        return response;
    }
}
