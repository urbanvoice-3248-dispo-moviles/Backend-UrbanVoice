package com.upc.pre.urbanvoiceapp.reports.interfaces.rest.assemblers;

import com.upc.pre.urbanvoiceapp.reports.application.commands.CreateIncidentReportCommand;
import com.upc.pre.urbanvoiceapp.reports.application.commands.UpdateIncidentReportCommand;
import com.upc.pre.urbanvoiceapp.reports.domain.entities.IncidentReport;
import com.upc.pre.urbanvoiceapp.reports.interfaces.rest.resources.CreateIncidentReportResource;
import com.upc.pre.urbanvoiceapp.reports.interfaces.rest.resources.IncidentReportResponse;
import com.upc.pre.urbanvoiceapp.reports.interfaces.rest.resources.UpdateIncidentReportResource;
import org.springframework.stereotype.Component;

/**
 * Assembler para convertir entre DTOs (Resources), Commands y Domain Models.
 *
 * <p>Centraliza la traduccion de nombres y estructura para que el controlador no
 * conozca detalles del modelo de dominio ni de los comandos de aplicacion.</p>
 */
@Component
public class IncidentReportAssembler {

    /**
     * Convierte un recurso de creacion en comando de aplicacion.
     *
     * @param userId usuario propietario del reporte.
     * @param resource payload HTTP de creacion.
     * @return comando listo para el servicio de aplicacion.
     */
    public CreateIncidentReportCommand toCreateCommand(Long userId, CreateIncidentReportResource resource) {
        return new CreateIncidentReportCommand(
                userId,
                resource.getIncidentType(),
                resource.getTitle(),
                resource.getDescription(),
                resource.getLatitude(),
                resource.getLongitude(),
                resource.getAddress(),
                resource.getMediaUrl(),
                resource.getIsAnonymous()
        );
    }

    /**
     * Convierte un recurso de actualizacion en comando de aplicacion.
     *
     * @param reportId identificador del reporte a modificar.
     * @param resource payload HTTP de actualizacion.
     * @return comando listo para el servicio de aplicacion.
     */
    public UpdateIncidentReportCommand toUpdateCommand(Long reportId, UpdateIncidentReportResource resource) {
        return new UpdateIncidentReportCommand(
                reportId,
                resource.getTitle(),
                resource.getDescription(),
                resource.getMediaUrl()
        );
    }

    /**
     * Convierte un agregado de dominio en recurso de respuesta REST.
     *
     * @param report agregado de dominio a exponer.
     * @return DTO serializable para el cliente HTTP.
     */
    public IncidentReportResponse toResponse(IncidentReport report) {
        IncidentReportResponse response = new IncidentReportResponse();
        response.setId(report.getId());
        response.setUserId(report.getUserId());
        response.setTitle(report.getTitle());
        response.setDescription(report.getDescription());
        response.setIncidentType(report.getIncidentType().getType());
        response.setLatitude(report.getLocation().getLatitude());
        response.setLongitude(report.getLocation().getLongitude());
        response.setAddress(report.getLocation().getAddress());
        response.setMediaUrl(report.getMediaUrl());
        response.setIsAnonymous(report.isAnonymous());
        response.setReportedAt(report.getReportedAt());
        return response;
    }
}
