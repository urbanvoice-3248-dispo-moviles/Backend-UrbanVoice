package com.upc.pre.urbanvoiceapp.reports.interfaces.rest.assemblers;

import com.upc.pre.urbanvoiceapp.reports.application.commands.CreateIncidentReportCommand;
import com.upc.pre.urbanvoiceapp.reports.application.commands.UpdateIncidentReportCommand;
import com.upc.pre.urbanvoiceapp.reports.domain.entities.IncidentReport;
import com.upc.pre.urbanvoiceapp.reports.interfaces.rest.resources.CreateIncidentReportResource;
import com.upc.pre.urbanvoiceapp.reports.interfaces.rest.resources.IncidentReportResponse;
import com.upc.pre.urbanvoiceapp.reports.interfaces.rest.resources.UpdateIncidentReportResource;
import org.springframework.stereotype.Component;

/**
 * Assembler para convertir entre DTOs (Resources) y Commands/Domain Models.
 */
@Component
public class IncidentReportAssembler {

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

    public UpdateIncidentReportCommand toUpdateCommand(Long reportId, UpdateIncidentReportResource resource) {
        return new UpdateIncidentReportCommand(
                reportId,
                resource.getTitle(),
                resource.getDescription(),
                resource.getMediaUrl()
        );
    }

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
        response.setStatus(report.getStatus());
        return response;
    }
}
