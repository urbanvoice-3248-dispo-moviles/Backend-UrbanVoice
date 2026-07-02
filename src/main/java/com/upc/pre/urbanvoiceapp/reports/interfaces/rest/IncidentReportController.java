package com.upc.pre.urbanvoiceapp.reports.interfaces.rest;

import com.upc.pre.urbanvoiceapp.reports.application.commands.CreateIncidentReportCommand;
import com.upc.pre.urbanvoiceapp.reports.application.commands.DeleteIncidentReportCommand;
import com.upc.pre.urbanvoiceapp.reports.application.commands.UpdateIncidentReportCommand;
import com.upc.pre.urbanvoiceapp.reports.application.queries.FindNearbyIncidentsQuery;
import com.upc.pre.urbanvoiceapp.reports.application.queries.GetIncidentReportByIdQuery;
import com.upc.pre.urbanvoiceapp.reports.application.queries.GetIncidentReportsByUserIdQuery;
import com.upc.pre.urbanvoiceapp.reports.application.services.IncidentReportApplicationService;
import com.upc.pre.urbanvoiceapp.reports.interfaces.rest.assemblers.IncidentReportAssembler;
import com.upc.pre.urbanvoiceapp.reports.interfaces.rest.resources.CreateIncidentReportResource;
import com.upc.pre.urbanvoiceapp.reports.interfaces.rest.resources.IncidentReportResponse;
import com.upc.pre.urbanvoiceapp.reports.interfaces.rest.resources.UpdateIncidentReportResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller para gestionar reportes de incidentes.
 * Endpoints: /api/v1/reports
 */
@RestController
@RequestMapping("/api/v1/reports")
@Tag(name = "Incident Reports", description = "API para gestionar reportes de incidentes")
public class IncidentReportController {

    private final IncidentReportApplicationService reportService;
    private final IncidentReportAssembler reportAssembler;

    public IncidentReportController(IncidentReportApplicationService reportService,
                                   IncidentReportAssembler reportAssembler) {
        this.reportService = reportService;
        this.reportAssembler = reportAssembler;
    }

    @PostMapping
    @Operation(summary = "Crear nuevo reporte de incidente")
    public ResponseEntity<IncidentReportResponse> createIncidentReport(
            @RequestHeader(value = "X-User-ID", required = true) Long userId,
            @RequestBody CreateIncidentReportResource resource) {
        CreateIncidentReportCommand command = reportAssembler.toCreateCommand(userId, resource);
        var report = reportService.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reportAssembler.toResponse(report));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener reporte por ID")
    public ResponseEntity<IncidentReportResponse> getIncidentReportById(@PathVariable Long id) {
        GetIncidentReportByIdQuery query = new GetIncidentReportByIdQuery(id);
        var report = reportService.handle(query);
        return ResponseEntity.ok(reportAssembler.toResponse(report));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Obtener reportes de un usuario")
    public ResponseEntity<List<IncidentReportResponse>> getIncidentReportsByUserId(@PathVariable Long userId) {
        GetIncidentReportsByUserIdQuery query = new GetIncidentReportsByUserIdQuery(userId);
        var reports = reportService.handle(query);
        var responses = reports.stream()
                .map(reportAssembler::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/all")
    @Operation(summary = "Obtener todos los reportes (moderación)")
    public ResponseEntity<List<IncidentReportResponse>> getAllIncidentReports() {
        var reports = reportService.getAllReports();
        var responses = reports.stream()
                .map(reportAssembler::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/statistics")
    @Operation(summary = "Obtener estadísticas de reportes")
    public ResponseEntity<java.util.Map<String, Object>> getStatistics() {
        return ResponseEntity.ok(reportService.getStatistics());
    }

    @GetMapping("/nearby")
    @Operation(summary = "Obtener reportes cercanos a una ubicación")
    public ResponseEntity<List<IncidentReportResponse>> getNearbyIncidents(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam(defaultValue = "5.0") Double radiusInKm) {
        FindNearbyIncidentsQuery query = new FindNearbyIncidentsQuery(latitude, longitude, radiusInKm);
        var reports = reportService.handle(query);
        var responses = reports.stream()
                .map(reportAssembler::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reporte de incidente")
    public ResponseEntity<IncidentReportResponse> updateIncidentReport(
            @PathVariable Long id,
            @RequestBody UpdateIncidentReportResource resource) {
        UpdateIncidentReportCommand command = reportAssembler.toUpdateCommand(id, resource);
        var report = reportService.handle(command);
        return ResponseEntity.ok(reportAssembler.toResponse(report));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reporte de incidente")
    public ResponseEntity<Void> deleteIncidentReport(@PathVariable Long id) {
        DeleteIncidentReportCommand command = new DeleteIncidentReportCommand(id);
        reportService.handle(command);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/approve")
    @Operation(summary = "Aprobar reporte de incidente")
    public ResponseEntity<IncidentReportResponse> approveIncidentReport(@PathVariable Long id) {
        var report = reportService.approveReport(id);
        return ResponseEntity.ok(reportAssembler.toResponse(report));
    }

    @PutMapping("/{id}/reject")
    @Operation(summary = "Rechazar reporte de incidente")
    public ResponseEntity<IncidentReportResponse> rejectIncidentReport(@PathVariable Long id) {
        var report = reportService.rejectReport(id);
        return ResponseEntity.ok(reportAssembler.toResponse(report));
    }

}
