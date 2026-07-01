package com.upc.pre.urbanvoiceapp.districts.interfaces.rest;

import com.upc.pre.urbanvoiceapp.districts.application.commands.CreateDistrictCommand;
import com.upc.pre.urbanvoiceapp.districts.application.commands.DeleteDistrictCommand;
import com.upc.pre.urbanvoiceapp.districts.application.commands.UpdateDistrictCommand;
import com.upc.pre.urbanvoiceapp.districts.application.queries.GetDistrictByIdQuery;
import com.upc.pre.urbanvoiceapp.districts.application.queries.GetDistrictByNameQuery;
import com.upc.pre.urbanvoiceapp.districts.application.services.DistrictApplicationService;
import com.upc.pre.urbanvoiceapp.districts.interfaces.rest.assemblers.DistrictAssembler;
import com.upc.pre.urbanvoiceapp.districts.interfaces.rest.resources.CreateDistrictResource;
import com.upc.pre.urbanvoiceapp.districts.interfaces.rest.resources.DistrictResponse;
import com.upc.pre.urbanvoiceapp.districts.interfaces.rest.resources.UpdateDistrictResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/districts")
@Tag(name = "Districts", description = "API para gestionar distritos con niveles de riesgo y polígonos")
public class DistrictController {

    private final DistrictApplicationService districtService;
    private final DistrictAssembler districtAssembler;

    public DistrictController(DistrictApplicationService districtService, DistrictAssembler districtAssembler) {
        this.districtService = districtService;
        this.districtAssembler = districtAssembler;
    }

    @PostMapping
    @Operation(summary = "Crear nuevo distrito con polígono y nivel de riesgo")
    public ResponseEntity<DistrictResponse> createDistrict(@RequestBody CreateDistrictResource resource) {
        CreateDistrictCommand command = districtAssembler.toCreateCommand(resource);
        var district = districtService.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(districtAssembler.toResponse(district));
    }

    @GetMapping
    @Operation(summary = "Obtener todos los distritos")
    public ResponseEntity<List<DistrictResponse>> getAllDistricts() {
        var districts = districtService.getAllDistricts();
        var responses = districts.stream()
                .map(districtAssembler::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener distrito por ID")
    public ResponseEntity<DistrictResponse> getDistrictById(@PathVariable Long id) {
        GetDistrictByIdQuery query = new GetDistrictByIdQuery(id);
        var district = districtService.handle(query);
        return ResponseEntity.ok(districtAssembler.toResponse(district));
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Obtener distrito por nombre")
    public ResponseEntity<DistrictResponse> getDistrictByName(@PathVariable String name) {
        GetDistrictByNameQuery query = new GetDistrictByNameQuery(name);
        var district = districtService.handle(query);
        return ResponseEntity.ok(districtAssembler.toResponse(district));
    }

    @GetMapping("/dangerous")
    @Operation(summary = "Obtener distritos con riesgo mínimo")
    public ResponseEntity<List<DistrictResponse>> getDangerousDistricts(
            @RequestParam(defaultValue = "3") Integer minRiskLevel) {
        var districts = districtService.getDangerousDistricts(minRiskLevel);
        var responses = districts.stream()
                .map(districtAssembler::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar distrito")
    public ResponseEntity<DistrictResponse> updateDistrict(
            @PathVariable Long id,
            @RequestBody UpdateDistrictResource resource) {
        UpdateDistrictCommand command = districtAssembler.toUpdateCommand(id, resource);
        var district = districtService.handle(command);
        return ResponseEntity.ok(districtAssembler.toResponse(district));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar distrito")
    public ResponseEntity<Void> deleteDistrict(@PathVariable Long id) {
        DeleteDistrictCommand command = new DeleteDistrictCommand(id);
        districtService.handle(command);
        return ResponseEntity.noContent().build();
    }
}
