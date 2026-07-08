package com.upc.pre.urbanvoiceapp.notifications.application.eventhandlers;

import com.upc.pre.urbanvoiceapp.districts.application.services.DistrictApplicationService;
import com.upc.pre.urbanvoiceapp.notifications.domain.entities.Alert;
import com.upc.pre.urbanvoiceapp.notifications.domain.entities.UserAlertConfig;
import com.upc.pre.urbanvoiceapp.notifications.domain.repositories.AlertRepository;
import com.upc.pre.urbanvoiceapp.notifications.domain.repositories.UserAlertConfigRepository;
import com.upc.pre.urbanvoiceapp.notifications.domain.services.AlertDomainService;
import com.upc.pre.urbanvoiceapp.notifications.domain.valueobjects.AlertType;
import com.upc.pre.urbanvoiceapp.profiles.domain.repositories.UserProfileRepository;
import com.upc.pre.urbanvoiceapp.reports.domain.events.IncidentReportedEvent;
import com.upc.pre.urbanvoiceapp.shared.locationsharing.domain.entities.UserLiveLocation;
import com.upc.pre.urbanvoiceapp.shared.locationsharing.domain.repositories.UserLiveLocationRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class IncidentReportedEventHandler {

    private final AlertDomainService alertService;
    private final AlertRepository alertRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserAlertConfigRepository configRepository;
    private final UserLiveLocationRepository userLiveLocationRepository;
    private final DistrictApplicationService districtService;

    public IncidentReportedEventHandler(AlertDomainService alertService,
                                        AlertRepository alertRepository,
                                        UserProfileRepository userProfileRepository,
                                        UserAlertConfigRepository configRepository,
                                        UserLiveLocationRepository userLiveLocationRepository,
                                        DistrictApplicationService districtService) {
        this.alertService = alertService;
        this.alertRepository = alertRepository;
        this.userProfileRepository = userProfileRepository;
        this.configRepository = configRepository;
        this.userLiveLocationRepository = userLiveLocationRepository;
        this.districtService = districtService;
    }

    @EventListener
    public void onIncidentReported(IncidentReportedEvent event) {
        String title = "Nuevo incidente: " + event.getType();
        String message = "Se ha reportado un " + event.getType().toLowerCase() +
                " en " + event.getLocation();

        userProfileRepository.findAll().forEach(profile -> {
            if (!shouldNotifyUser(profile.getId(), event.getLatitude(), event.getLongitude())) {
                return;
            }

            Alert alert = new Alert(
                    profile.getId(),
                    AlertType.INCIDENT_NEARBY,
                    title,
                    message,
                    event.getLatitude(),
                    event.getLongitude()
            );
            alert.validate();
            alertRepository.save(alert);
            alertService.sendPushNotification(alert);
        });

        districtService.recordIncidentAtLocation(event.getLatitude(), event.getLongitude());
    }

    private boolean shouldNotifyUser(Long userId, double incidentLat, double incidentLon) {
        Optional<UserAlertConfig> configOpt = configRepository.findByUserId(userId);
        if (configOpt.isEmpty()) return true;
        UserAlertConfig config = configOpt.get();
        if (!config.isEnabled()) return false;

        double radius = config.getRadiusInKm();
        if (radius <= 0) return true;

        Optional<UserLiveLocation> locOpt = userLiveLocationRepository.findByUserId(userId);
        if (locOpt.isEmpty()) return true;

        UserLiveLocation userLoc = locOpt.get();
        double distance = distanceKm(
                userLoc.getLatitude(), userLoc.getLongitude(),
                incidentLat, incidentLon
        );
        return distance <= radius;
    }

    private double distanceKm(double lat1, double lon1, double lat2, double lon2) {
        double r = 6371.0;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return r * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}
