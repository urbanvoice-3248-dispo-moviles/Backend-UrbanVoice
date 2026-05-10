package com.upc.pre.urbanvoiceapp.controllers;

import com.upc.pre.urbanvoiceapp.models.Report;
import com.upc.pre.urbanvoiceapp.schemas.ReportSchema;
import com.upc.pre.urbanvoiceapp.services.ReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/reports")
@Tag(name = "Reports", description = "Reports Endpoints")
public class ReportController {
    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    @PostMapping("/")
    public ResponseEntity<?> newReport(@RequestBody ReportSchema report) {
        try {
            Report newReport = service.saveReport(report);
            return ResponseEntity.created(null).body(newReport);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllReports() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReportById(@PathVariable Long id) {
        try {
            Report report = service.findById(id);
            if (report == null) {
                return ResponseEntity.badRequest().body(Map.of("message", "Report not found"));
            }
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getReportsByUserId(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(service.findByUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReportById(@PathVariable Long id) {
        try {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
