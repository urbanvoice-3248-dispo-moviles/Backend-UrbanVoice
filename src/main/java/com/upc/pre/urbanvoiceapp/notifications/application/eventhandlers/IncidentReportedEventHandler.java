package com.upc.pre.urbanvoiceapp.notifications.application.eventhandlers;

import com.upc.pre.urbanvoiceapp.notifications.domain.entities.Alert;
import com.upc.pre.urbanvoiceapp.notifications.domain.repositories.AlertRepository;
import com.upc.pre.urbanvoiceapp.notifications.domain.services.AlertDomainService;
import com.upc.pre.urbanvoiceapp.notifications.domain.valueobjects.AlertType;
import com.upc.pre.urbanvoiceapp.profiles.domain.repositories.UserProfileRepository;
import com.upc.pre.urbanvoiceapp.reports.domain.events.IncidentReportedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class IncidentReportedEventHandler {

    private final AlertDomainService alertService;
    private final AlertRepository alertRepository;
    private final UserProfileRepository userProfileRepository;

    public IncidentReportedEventHandler(AlertDomainService alertService,
                                        AlertRepository alertRepository,
                                        UserProfileRepository userProfileRepository) {
        this.alertService = alertService;
        this.alertRepository = alertRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @EventListener
    public void onIncidentReported(IncidentReportedEvent event) {
        String title = "Nuevo incidente: " + event.getType();
        String message = "Se ha reportado un " + event.getType().toLowerCase() +
                " en " + event.getLocation();

        userProfileRepository.findAll().forEach(profile -> {
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
    }
}
