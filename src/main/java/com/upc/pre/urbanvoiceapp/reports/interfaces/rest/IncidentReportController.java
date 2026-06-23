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
        try {
            CreateIncidentReportCommand command = reportAssembler.toCreateCommand(userId, resource);
            var report = reportService.handle(command);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(reportAssembler.toResponse(report));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener reporte por ID")
    public ResponseEntity<IncidentReportResponse> getIncidentReportById(@PathVariable Long id) {
        try {
            GetIncidentReportByIdQuery query = new GetIncidentReportByIdQuery(id);
            var report = reportService.handle(query);
            return report.map(r -> ResponseEntity.ok(reportAssembler.toResponse(r)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Obtener reportes de un usuario")
    public ResponseEntity<List<IncidentReportResponse>> getIncidentReportsByUserId(@PathVariable Long userId) {
        try {
            GetIncidentReportsByUserIdQuery query = new GetIncidentReportsByUserIdQuery(userId);
            var reports = reportService.handle(query);
            var responses = reports.stream()
                    .map(reportAssembler::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    @Operation(summary = "Obtener todos los reportes (moderación)")
    public ResponseEntity<List<IncidentReportResponse>> getAllIncidentReports() {
        try {
            var reports = reportService.getAllReports();
            var responses = reports.stream()
                    .map(reportAssembler::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/nearby")
    @Operation(summary = "Obtener reportes cercanos a una ubicación")
    public ResponseEntity<List<IncidentReportResponse>> getNearbyIncidents(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam(defaultValue = "5.0") Double radiusInKm) {
        try {
            FindNearbyIncidentsQuery query = new FindNearbyIncidentsQuery(latitude, longitude, radiusInKm);
            var reports = reportService.handle(query);
            var responses = reports.stream()
                    .map(reportAssembler::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reporte de incidente")
    public ResponseEntity<IncidentReportResponse> updateIncidentReport(
            @PathVariable Long id,
            @RequestBody UpdateIncidentReportResource resource) {
        try {
            UpdateIncidentReportCommand command = reportAssembler.toUpdateCommand(id, resource);
            var report = reportService.handle(command);
            return ResponseEntity.ok(reportAssembler.toResponse(report));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reporte de incidente")
    public ResponseEntity<?> deleteIncidentReport(@PathVariable Long id) {
        try {
            DeleteIncidentReportCommand command = new DeleteIncidentReportCommand(id);
            reportService.handle(command);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
