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
 * Endpoints base: {@code /api/v1/reports}.
 *
 * <p>Traduce solicitudes HTTP a comandos y queries de aplicacion, y transforma
 * los agregados resultantes en recursos de respuesta.</p>
 */
@RestController
@RequestMapping("/api/v1/reports")
@Tag(name = "Incident Reports", description = "API para gestionar reportes de incidentes")
public class IncidentReportController {

    private final IncidentReportApplicationService reportService;
    private final IncidentReportAssembler reportAssembler;

    /**
     * Construye el controlador de reportes.
     *
     * @param reportService servicio de aplicacion de reportes.
     * @param reportAssembler assembler de recursos REST y mensajes de aplicacion.
     */
    public IncidentReportController(IncidentReportApplicationService reportService,
                                   IncidentReportAssembler reportAssembler) {
        this.reportService = reportService;
        this.reportAssembler = reportAssembler;
    }

    /**
     * Crea un nuevo reporte de incidente para el usuario autenticado por cabecera.
     *
     * @param userId identificador de usuario recibido en {@code X-User-ID}.
     * @param resource payload con los datos del incidente.
     * @return respuesta HTTP 201 con el reporte creado.
     */
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

    /**
     * Obtiene un reporte por identificador.
     *
     * @param id identificador del reporte.
     * @return respuesta HTTP 200 con el reporte encontrado.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener reporte por ID")
    public ResponseEntity<IncidentReportResponse> getIncidentReportById(@PathVariable Long id) {
        GetIncidentReportByIdQuery query = new GetIncidentReportByIdQuery(id);
        var report = reportService.handle(query);
        return ResponseEntity.ok(reportAssembler.toResponse(report));
    }

    /**
     * Lista reportes pertenecientes a un usuario.
     *
     * @param userId identificador del usuario.
     * @return respuesta HTTP 200 con los reportes del usuario.
     */
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

    /**
     * Lista todos los reportes disponibles para moderacion.
     *
     * @return respuesta HTTP 200 con todos los reportes.
     */
    @GetMapping("/all")
    @Operation(summary = "Obtener todos los reportes (moderacion)")
    public ResponseEntity<List<IncidentReportResponse>> getAllIncidentReports() {
        var reports = reportService.getAllReports();
        var responses = reports.stream()
                .map(reportAssembler::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Busca reportes cercanos a una coordenada.
     *
     * @param latitude latitud del punto central.
     * @param longitude longitud del punto central.
     * @param radiusInKm radio de busqueda en kilometros; por defecto 5 km.
     * @return respuesta HTTP 200 con los reportes cercanos.
     */
    @GetMapping("/nearby")
    @Operation(summary = "Obtener reportes cercanos a una ubicacion")
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

    /**
     * Actualiza parcialmente un reporte de incidente.
     *
     * @param id identificador del reporte a actualizar.
     * @param resource payload con los campos editables.
     * @return respuesta HTTP 200 con el reporte actualizado.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reporte de incidente")
    public ResponseEntity<IncidentReportResponse> updateIncidentReport(
            @PathVariable Long id,
            @RequestBody UpdateIncidentReportResource resource) {
        UpdateIncidentReportCommand command = reportAssembler.toUpdateCommand(id, resource);
        var report = reportService.handle(command);
        return ResponseEntity.ok(reportAssembler.toResponse(report));
    }

    /**
     * Elimina un reporte de incidente.
     *
     * @param id identificador del reporte a eliminar.
     * @return respuesta HTTP 204 cuando la eliminacion se completa.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reporte de incidente")
    public ResponseEntity<Void> deleteIncidentReport(@PathVariable Long id) {
        DeleteIncidentReportCommand command = new DeleteIncidentReportCommand(id);
        reportService.handle(command);
        return ResponseEntity.noContent().build();
    }

}
