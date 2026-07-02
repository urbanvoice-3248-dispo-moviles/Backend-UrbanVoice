package com.upc.pre.urbanvoiceapp.reports.domain.repositories;

import com.upc.pre.urbanvoiceapp.reports.domain.entities.IncidentReport;

import java.util.Optional;
import java.util.List;

/**
 * Repository interface para el Aggregate Root IncidentReport.
 */
public interface IncidentReportRepository {
    
    /**
     * Guarda un nuevo reporte o actualiza uno existente.
     */
    IncidentReport save(IncidentReport report);

    /**
     * Busca un reporte por su ID.
     */
    Optional<IncidentReport> findById(Long id);

    /**
     * Obtiene todos los reportes de un usuario específico.
     */
    List<IncidentReport> findByUserId(Long userId);

    /**
     * Obtiene los reportes más recientes dentro de un radio de distancia.
     */
    List<IncidentReport> findNearby(double latitude, double longitude, double radiusInKm);

    /**
     * Elimina un reporte por su ID.
     */
    void deleteById(Long id);

    /**
     * Obtiene todos los reportes.
     */
    List<IncidentReport> findAll();

    long count();

    java.util.Map<String, Long> countByIncidentType();

    java.util.Map<String, Long> countByStatus();
}
