package com.upc.pre.urbanvoiceapp.districts.interfaces.rest;

import com.upc.pre.urbanvoiceapp.districts.application.services.routeassessment.RouteAssessmentService;
import com.upc.pre.urbanvoiceapp.districts.interfaces.rest.resources.routeassessment.RouteAssessmentRequest;
import com.upc.pre.urbanvoiceapp.districts.interfaces.rest.resources.routeassessment.RouteAssessmentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/routes")
@Tag(name = "Route Assessment", description = "API para evaluar riesgo de rutas basado en niveles de alerta de distritos")
public class RouteAssessmentController {

    private static final Logger logger = LoggerFactory.getLogger(RouteAssessmentController.class);
    private final RouteAssessmentService routeAssessmentService;

    public RouteAssessmentController(RouteAssessmentService routeAssessmentService) {
        this.routeAssessmentService = routeAssessmentService;
    }

    @PostMapping("/assess")
    @Operation(summary = "Evaluar nivel de riesgo de una ruta basada en distritos")
    public ResponseEntity<RouteAssessmentResponse> assessRoute(@RequestBody RouteAssessmentRequest request) {
        logger.info("Assessing route with {} waypoints", request.getWaypoints().size());
        RouteAssessmentResponse response = routeAssessmentService.assessRoute(request);
        return ResponseEntity.ok(response);
    }
}
