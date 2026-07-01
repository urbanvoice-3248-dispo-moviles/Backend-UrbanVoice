package com.upc.pre.urbanvoiceapp.reports.domain.repositories;

import com.upc.pre.urbanvoiceapp.reports.domain.entities.IncidentReport;

import java.util.Optional;
import java.util.List;

/**
 * Repository interface para el Aggregate Root IncidentReport.
 *
 * <p>Define el contrato de persistencia requerido por la capa de aplicacion sin
 * exponer detalles de JPA ni consultas SQL al dominio.</p>
 */
public interface IncidentReportRepository {

    /**
     * Guarda un nuevo reporte o actualiza uno existente.
     *
     * @param report agregado que se debe persistir.
     * @return agregado persistido con los datos generados por almacenamiento.
     */
    IncidentReport save(IncidentReport report);

    /**
     * Busca un reporte por su ID.
     *
     * @param id identificador del reporte.
     * @return reporte encontrado o {@link Optional#empty()} si no existe.
     */
    Optional<IncidentReport> findById(Long id);

    /**
     * Obtiene todos los reportes de un usuario especifico.
     *
     * @param userId identificador del usuario.
     * @return reportes registrados por el usuario.
     */
    List<IncidentReport> findByUserId(Long userId);

    /**
     * Obtiene los reportes mas recientes dentro de un radio de distancia.
     *
     * @param latitude latitud del punto central.
     * @param longitude longitud del punto central.
     * @param radiusInKm radio de busqueda en kilometros.
     * @return reportes ubicados dentro del radio indicado.
     */
    List<IncidentReport> findNearby(double latitude, double longitude, double radiusInKm);

    /**
     * Elimina un reporte por su ID.
     *
     * @param id identificador del reporte a eliminar.
     */
    void deleteById(Long id);

    /**
     * Obtiene todos los reportes.
     *
     * @return lista completa de reportes persistidos.
     */
    List<IncidentReport> findAll();
}
