package com.upc.pre.urbanvoiceapp.reports.application.services;

import com.upc.pre.urbanvoiceapp.reports.application.commands.CreateIncidentReportCommand;
import com.upc.pre.urbanvoiceapp.reports.application.commands.DeleteIncidentReportCommand;
import com.upc.pre.urbanvoiceapp.reports.application.commands.UpdateIncidentReportCommand;
import com.upc.pre.urbanvoiceapp.reports.application.queries.FindNearbyIncidentsQuery;
import com.upc.pre.urbanvoiceapp.reports.application.queries.GetIncidentReportByIdQuery;
import com.upc.pre.urbanvoiceapp.reports.application.queries.GetIncidentReportsByUserIdQuery;
import com.upc.pre.urbanvoiceapp.reports.domain.entities.IncidentReport;
import com.upc.pre.urbanvoiceapp.reports.domain.exceptions.IncidentReportNotFoundException;
import com.upc.pre.urbanvoiceapp.reports.domain.repositories.IncidentReportRepository;
import com.upc.pre.urbanvoiceapp.reports.domain.valueobjects.GeoLocation;
import com.upc.pre.urbanvoiceapp.reports.domain.valueobjects.IncidentType;
import com.upc.pre.urbanvoiceapp.shared.domain.events.DomainEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Application Service para orquestar operaciones sobre reportes de incidentes.
 * Core del negocio de UrbanVoice - gestión de reportes de incidentes.
 */
@Service
@Transactional
public class IncidentReportApplicationService {

    private final IncidentReportRepository reportRepository;
    private final ApplicationEventPublisher eventPublisher;

    public IncidentReportApplicationService(IncidentReportRepository reportRepository,
                                            ApplicationEventPublisher eventPublisher) {
        this.reportRepository = reportRepository;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Maneja la creación de un nuevo reporte de incidente.
     * Valida el tipo de incidente y la ubicación antes de crear el reporte.
     */
    public IncidentReport handle(CreateIncidentReportCommand command) {
        // Validar que el tipo de incidente sea válido
        IncidentType incidentType = new IncidentType(
                command.getIncidentType(),
                command.getIncidentType()
        );

        // Crear value objects de dominio
        GeoLocation location = new GeoLocation(
                command.getLatitude(),
                command.getLongitude(),
                command.getAddress()
        );

        // Crear el agregado de dominio
        IncidentReport report = new IncidentReport(
                command.getUserId(),
                incidentType,
                location,
                command.getTitle(),
                command.getDescription(),
                command.getMediaUrl(),
                command.getIsAnonymous()
        );

        report.validate();
        report = reportRepository.save(report);

        report.recordCreation();
        for (DomainEvent event : report.pullDomainEvents()) {
            eventPublisher.publishEvent(event);
        }

        return report;
    }

    /**
     * Maneja la actualización de un reporte existente.
     * Solo permite actualizar reportes que aún no han sido resueltos.
     */
    public IncidentReport handle(UpdateIncidentReportCommand command) {
        IncidentReport report = reportRepository.findById(command.getReportId())
                .orElseThrow(() -> new IncidentReportNotFoundException(command.getReportId()));

        // Actualizar título si se proporciona
        if (command.getTitle() != null) {
            report.setTitle(command.getTitle());
        }

        // Actualizar descripción si se proporciona
        if (command.getDescription() != null) {
            report.updateDescription(command.getDescription());
        }

        // Actualizar media si se proporciona
        if (command.getMediaUrl() != null) {
            report.setMediaUrl(command.getMediaUrl());
        }

        report.validate();
        return reportRepository.save(report);
    }

    /**
     * Maneja la eliminación de un reporte.
     */
    public void handle(DeleteIncidentReportCommand command) {
        reportRepository.deleteById(command.getReportId());
    }

    /**
     * Maneja la consulta de un reporte por ID.
     */
    @Transactional(readOnly = true)
    public IncidentReport handle(GetIncidentReportByIdQuery query) {
        return reportRepository.findById(query.getReportId())
                .orElseThrow(() -> new IncidentReportNotFoundException(query.getReportId()));
    }

    /**
     * Maneja la consulta de reportes por usuario.
     */
    @Transactional(readOnly = true)
    public List<IncidentReport> handle(GetIncidentReportsByUserIdQuery query) {
        return reportRepository.findByUserId(query.getUserId());
    }

    /**
     * Maneja la consulta de reportes cercanos a una ubicación.
     */
    @Transactional(readOnly = true)
    public List<IncidentReport> handle(FindNearbyIncidentsQuery query) {
        return reportRepository.findNearby(query.getLatitude(), query.getLongitude(), query.getRadiusInKm());
    }

    /**
     * Obtiene todos los reportes (para moderación).
     */
    @Transactional(readOnly = true)
    public List<IncidentReport> getAllReports() {
        return reportRepository.findAll();
    }

    @Transactional(readOnly = true)
    public java.util.Map<String, Object> getStatistics() {
        java.util.Map<String, Object> stats = new java.util.HashMap<>();
        stats.put("total_reports", reportRepository.count());
        stats.put("reports_by_type", reportRepository.countByIncidentType());
        stats.put("reports_by_status", reportRepository.countByStatus());
        return stats;
    }

    public IncidentReport approveReport(Long reportId) {
        IncidentReport report = reportRepository.findById(reportId)
                .orElseThrow(() -> new IncidentReportNotFoundException(reportId));
        report.setStatus("APPROVED");
        return reportRepository.save(report);
    }

    public IncidentReport rejectReport(Long reportId) {
        IncidentReport report = reportRepository.findById(reportId)
                .orElseThrow(() -> new IncidentReportNotFoundException(reportId));
        report.setStatus("REJECTED");
        return reportRepository.save(report);
    }
}
