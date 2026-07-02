package com.upc.pre.urbanvoiceapp.reports.interfaces.rest;

import com.upc.pre.urbanvoiceapp.reports.application.services.IncidentCategoryApplicationService;
import com.upc.pre.urbanvoiceapp.reports.interfaces.rest.resources.CreateIncidentCategoryResource;
import com.upc.pre.urbanvoiceapp.reports.interfaces.rest.resources.IncidentCategoryResponse;
import com.upc.pre.urbanvoiceapp.reports.interfaces.rest.resources.UpdateIncidentCategoryResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Incident Categories", description = "API para gestionar categorías de incidentes")
public class IncidentCategoryController {

    private final IncidentCategoryApplicationService categoryService;

    public IncidentCategoryController(IncidentCategoryApplicationService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @Operation(summary = "Crear nueva categoría")
    public ResponseEntity<IncidentCategoryResponse> createCategory(@RequestBody CreateIncidentCategoryResource resource) {
        var category = categoryService.createCategory(resource.getName(), resource.getDescription());
        URI location = URI.create("/api/v1/categories/" + category.getId());
        return ResponseEntity.created(location).body(toResponse(category));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener categoría por ID")
    public ResponseEntity<IncidentCategoryResponse> getCategoryById(@PathVariable Long id) {
        var category = categoryService.findById(id);
        return ResponseEntity.ok(toResponse(category));
    }

    @GetMapping
    @Operation(summary = "Obtener todas las categorías")
    public ResponseEntity<List<IncidentCategoryResponse>> getAllCategories() {
        var categories = categoryService.findAll();
        var responses = categories.stream().map(this::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar categoría")
    public ResponseEntity<IncidentCategoryResponse> updateCategory(
            @PathVariable Long id,
            @RequestBody UpdateIncidentCategoryResource resource) {
        var category = categoryService.updateCategory(id, resource.getName(), resource.getDescription());
        return ResponseEntity.ok(toResponse(category));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar categoría")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    private IncidentCategoryResponse toResponse(com.upc.pre.urbanvoiceapp.reports.domain.entities.IncidentCategory category) {
        IncidentCategoryResponse response = new IncidentCategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        return response;
    }
}
