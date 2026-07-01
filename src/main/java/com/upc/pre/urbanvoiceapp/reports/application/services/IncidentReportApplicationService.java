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
 * Core del negocio de UrbanVoice - gestion de reportes de incidentes.
 *
 * <p>Coordina comandos y queries del bounded context de reportes, delegando la
 * persistencia al repositorio de dominio y publicando eventos cuando el agregado
 * registra cambios relevantes.</p>
 */
@Service
@Transactional
public class IncidentReportApplicationService {

    private final IncidentReportRepository reportRepository;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Construye el servicio con sus dependencias de infraestructura.
     *
     * @param reportRepository repositorio del agregado {@link IncidentReport}.
     * @param eventPublisher publicador de eventos de Spring para eventos de dominio.
     */
    public IncidentReportApplicationService(IncidentReportRepository reportRepository,
                                            ApplicationEventPublisher eventPublisher) {
        this.reportRepository = reportRepository;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Maneja la creacion de un nuevo reporte de incidente.
     * Valida el tipo de incidente y la ubicacion antes de crear el reporte.
     *
     * @param command datos necesarios para crear el reporte.
     * @return reporte persistido con el identificador asignado.
     * @throws IllegalStateException si el agregado queda en un estado invalido.
     */
    public IncidentReport handle(CreateIncidentReportCommand command) {
        // Validar que el tipo de incidente sea valido
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
     * Maneja la actualizacion de un reporte existente.
     *
     * @param command cambios parciales solicitados para el reporte.
     * @return reporte actualizado y persistido.
     * @throws IncidentReportNotFoundException si no existe un reporte con el ID indicado.
     * @throws IllegalArgumentException si alguno de los campos editados viola reglas basicas.
     */
    public IncidentReport handle(UpdateIncidentReportCommand command) {
        IncidentReport report = reportRepository.findById(command.getReportId())
                .orElseThrow(() -> new IncidentReportNotFoundException(command.getReportId()));

        // Actualizar titulo si se proporciona
        if (command.getTitle() != null) {
            report.setTitle(command.getTitle());
        }

        // Actualizar descripcion si se proporciona
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
     * Maneja la eliminacion de un reporte.
     *
     * @param command identificador del reporte a eliminar.
     */
    public void handle(DeleteIncidentReportCommand command) {
        reportRepository.deleteById(command.getReportId());
    }

    /**
     * Maneja la consulta de un reporte por ID.
     *
     * @param query identificador del reporte solicitado.
     * @return reporte encontrado.
     * @throws IncidentReportNotFoundException si no existe un reporte con el ID indicado.
     */
    @Transactional(readOnly = true)
    public IncidentReport handle(GetIncidentReportByIdQuery query) {
        return reportRepository.findById(query.getReportId())
                .orElseThrow(() -> new IncidentReportNotFoundException(query.getReportId()));
    }

    /**
     * Maneja la consulta de reportes por usuario.
     *
     * @param query identificador del usuario consultado.
     * @return lista de reportes pertenecientes al usuario.
     */
    @Transactional(readOnly = true)
    public List<IncidentReport> handle(GetIncidentReportsByUserIdQuery query) {
        return reportRepository.findByUserId(query.getUserId());
    }

    /**
     * Maneja la consulta de reportes cercanos a una ubicacion.
     *
     * @param query punto central y radio de busqueda.
     * @return lista de reportes dentro del radio indicado.
     */
    @Transactional(readOnly = true)
    public List<IncidentReport> handle(FindNearbyIncidentsQuery query) {
        return reportRepository.findNearby(query.getLatitude(), query.getLongitude(), query.getRadiusInKm());
    }

    /**
     * Obtiene todos los reportes (para moderacion).
     *
     * @return lista completa de reportes registrados.
     */
    @Transactional(readOnly = true)
    public List<IncidentReport> getAllReports() {
        return reportRepository.findAll();
    }
}
