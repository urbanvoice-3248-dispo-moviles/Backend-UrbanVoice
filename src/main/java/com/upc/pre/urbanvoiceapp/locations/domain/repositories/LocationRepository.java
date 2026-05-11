package com.upc.pre.urbanvoiceapp.locations.domain.repositories;

import com.upc.pre.urbanvoiceapp.locations.domain.entities.Location;

import java.util.Optional;
import java.util.List;

/**
 * Repository interface para el Aggregate Root Location.
 */
public interface LocationRepository {
    
    /**
     * Guarda una nueva ubicación o actualiza una existente.
     */
    Location save(Location location);

    /**
     * Busca una ubicación por su ID.
     */
    Optional<Location> findById(Long id);

    /**
     * Obtiene ubicaciones cercanas.
     */
    List<Location> findNearby(double latitude, double longitude, double radiusInKm);

    /**
     * Obtiene ubicaciones por distrito.
     */
    List<Location> findByDistrict(String district);

    /**
     * Obtiene ubicaciones ordenadas por nivel de riesgo.
     */
    List<Location> findByRiskLevelGreaterThan(int minLevel);

    /**
     * Elimina una ubicación.
     */
    void deleteById(Long id);

    /**
     * Obtiene todas las ubicaciones.
     */
    List<Location> findAll();
}
