package com.upc.pre.urbanvoiceapp.locations.interfaces.rest;

import com.upc.pre.urbanvoiceapp.locations.application.services.LocationApplicationService;
import com.upc.pre.urbanvoiceapp.locations.interfaces.rest.resources.CreateLocationResource;
import com.upc.pre.urbanvoiceapp.locations.interfaces.rest.resources.LocationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/locations")
@Tag(name = "Locations", description = "API para gestionar ubicaciones exactas de reportes y alertas")
public class LocationController {

    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);
    private final LocationApplicationService locationService;

    public LocationController(LocationApplicationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    @Operation(summary = "Crear nueva ubicación exacta")
    public ResponseEntity<LocationResponse> createLocation(@Valid @RequestBody CreateLocationResource resource) {
        var location = locationService.createLocation(
                resource.getLatitude(),
                resource.getLongitude(),
                resource.getAddress(),
                resource.getDistrict()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toResponse(location));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener ubicación por ID")
    public ResponseEntity<LocationResponse> getLocationById(@PathVariable Long id) {
        var location = locationService.getLocationById(id);
        return ResponseEntity.ok(toResponse(location));
    }

    @GetMapping
    @Operation(summary = "Obtener todas las ubicaciones")
    public ResponseEntity<List<LocationResponse>> getAllLocations() {
        var locations = locationService.getAllLocations();
        var responses = locations.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/nearby")
    @Operation(summary = "Obtener ubicaciones cercanas")
    public ResponseEntity<List<LocationResponse>> getNearbyLocations(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam(defaultValue = "5.0") Double radiusInKm) {
        var locations = locationService.getNearbyLocations(latitude, longitude, radiusInKm);
        var responses = locations.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/district/{district}")
    @Operation(summary = "Obtener ubicaciones por distrito")
    public ResponseEntity<List<LocationResponse>> getLocationsByDistrict(@PathVariable String district) {
        var locations = locationService.getLocationsByDistrict(district);
        var responses = locations.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar ubicación")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }

    private LocationResponse toResponse(com.upc.pre.urbanvoiceapp.locations.domain.entities.Location location) {
        if (location == null) return null;
        LocationResponse response = new LocationResponse();
        response.setId(location.getId());
        response.setLatitude(location.getCoordinate().getLatitude());
        response.setLongitude(location.getCoordinate().getLongitude());
        response.setAddress(location.getAddress());
        response.setDistrict(location.getDistrict());
        response.setDescription(location.getDescription());
        response.setCreatedAt(location.getCreatedAt());
        return response;
    }
}
