package com.upc.pre.urbanvoiceapp.shared.locationsharing.interfaces.rest;

import com.upc.pre.urbanvoiceapp.shared.locationsharing.application.services.LocationSharingApplicationService;
import com.upc.pre.urbanvoiceapp.shared.locationsharing.domain.entities.LocationShareSession;
import com.upc.pre.urbanvoiceapp.shared.locationsharing.domain.entities.UserLiveLocation;
import com.upc.pre.urbanvoiceapp.shared.locationsharing.interfaces.rest.resources.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/location-sharing")
@Tag(name = "Location Sharing", description = "API para compartir ubicación en tiempo real entre usuarios")
public class LocationSharingController {

    private static final Logger logger = LoggerFactory.getLogger(LocationSharingController.class);
    private final LocationSharingApplicationService locationSharingService;

    public LocationSharingController(LocationSharingApplicationService locationSharingService) {
        this.locationSharingService = locationSharingService;
    }

    @PutMapping("/publish")
    @Operation(summary = "Publicar ubicación actual del usuario")
    public ResponseEntity<UserLiveLocationResponse> publishLocation(
            @RequestHeader("X-User-ID") Long userId,
            @RequestBody PublishLocationResource resource) {
        var location = locationSharingService.publishLocation(userId, resource.getLatitude(), resource.getLongitude());
        logger.info("User {} published location: {}, {}", userId, resource.getLatitude(), resource.getLongitude());
        return ResponseEntity.ok(toLocationResponse(location));
    }

    @GetMapping("/me")
    @Operation(summary = "Obtener mi propia ubicación")
    public ResponseEntity<UserLiveLocationResponse> getMyLocation(
            @RequestHeader("X-User-ID") Long userId) {
        return locationSharingService.getMyLocation(userId)
                .map(loc -> ResponseEntity.ok(toLocationResponse(loc)))
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/friends")
    @Operation(summary = "Obtener ubicaciones de usuarios que comparten conmigo")
    public ResponseEntity<List<UserLiveLocationResponse>> getFriendsLocations(
            @RequestHeader("X-User-ID") Long userId) {
        var locations = locationSharingService.getFriendsLocations(userId);
        var responses = locations.stream()
                .map(this::toLocationResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/shared-with-me")
    @Operation(summary = "Obtener mis propias ubicaciones compartidas hacia otros")
    public ResponseEntity<List<UserLiveLocationResponse>> getMySharedLocations(
            @RequestHeader("X-User-ID") Long userId) {
        var locations = locationSharingService.getMySharedLocations(userId);
        return ResponseEntity.ok(locations.stream().map(this::toLocationResponse).collect(Collectors.toList()));
    }

    @PostMapping("/share")
    @Operation(summary = "Empezar a compartir ubicación con otro usuario")
    public ResponseEntity<ShareSessionResponse> startSharing(
            @RequestHeader("X-User-ID") Long ownerUserId,
            @RequestBody ShareRequest request) {
        var session = locationSharingService.startSharing(ownerUserId, request.getTargetUserId());
        logger.info("User {} started sharing location with user {}", ownerUserId, request.getTargetUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(toSessionResponse(session));
    }

    @DeleteMapping("/share/{targetUserId}")
    @Operation(summary = "Dejar de compartir ubicación con un usuario")
    public ResponseEntity<Void> stopSharing(
            @RequestHeader("X-User-ID") Long ownerUserId,
            @PathVariable Long targetUserId) {
        locationSharingService.stopSharing(ownerUserId, targetUserId);
        logger.info("User {} stopped sharing location with user {}", ownerUserId, targetUserId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/shares")
    @Operation(summary = "Listar con quién comparto mi ubicación")
    public ResponseEntity<List<ShareSessionResponse>> getMyShares(
            @RequestHeader("X-User-ID") Long userId) {
        var sessions = locationSharingService.getMyShares(userId);
        return ResponseEntity.ok(sessions.stream().map(this::toSessionResponse).collect(Collectors.toList()));
    }

    private UserLiveLocationResponse toLocationResponse(UserLiveLocation location) {
        UserLiveLocationResponse response = new UserLiveLocationResponse();
        response.setUserId(location.getUserId());
        response.setLatitude(location.getLatitude());
        response.setLongitude(location.getLongitude());
        response.setUpdatedAt(location.getUpdatedAt());
        return response;
    }

    private ShareSessionResponse toSessionResponse(LocationShareSession session) {
        ShareSessionResponse response = new ShareSessionResponse();
        response.setId(session.getId());
        response.setOwnerUserId(session.getOwnerUserId());
        response.setTargetUserId(session.getTargetUserId());
        response.setActive(session.getActive());
        response.setCreatedAt(session.getCreatedAt());
        return response;
    }
}
