package com.upc.pre.urbanvoiceapp.notifications.application.eventhandlers;

import com.upc.pre.urbanvoiceapp.notifications.domain.services.AlertDomainService;
import com.upc.pre.urbanvoiceapp.notifications.domain.valueobjects.AlertType;
import com.upc.pre.urbanvoiceapp.reports.domain.events.IncidentReportedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Event Handler que escucha eventos de reportes de incidentes
 * y genera alertas para usuarios cercanos.
 */
@Component
public class IncidentReportedEventHandler {

    private final AlertDomainService alertService;

    public IncidentReportedEventHandler(AlertDomainService alertService) {
        this.alertService = alertService;
    }

    @EventListener
    public void onIncidentReported(IncidentReportedEvent event) {
        // Crear una alerta basada en el reporte de incidente
        // En una implementación real, se buscarían usuarios cercanos y se les enviaría la alerta
        
        String title = "Nuevo incidente reportado: " + event.getType();
        String message = "Se ha reportado un " + event.getType().toLowerCase() + 
                        " en " + event.getLocation();

        // Esta sería la ubicación del incidente
        Double latitude = event.getLatitude();
        Double longitude = event.getLongitude();

        System.out.println("Evento de incidente reportado: " + event.getEventName());
        System.out.println("Ubicación: " + latitude + ", " + longitude);
    }
}
