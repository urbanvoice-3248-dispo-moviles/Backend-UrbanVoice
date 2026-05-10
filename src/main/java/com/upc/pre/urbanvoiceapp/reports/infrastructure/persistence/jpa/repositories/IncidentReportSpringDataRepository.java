package com.upc.pre.urbanvoiceapp.reports.infrastructure.persistence.jpa.repositories;

import com.upc.pre.urbanvoiceapp.reports.infrastructure.persistence.jpa.entities.IncidentReportJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository para operaciones de persistencia de IncidentReportJpaEntity.
 */
@Repository
public interface IncidentReportSpringDataRepository extends JpaRepository<IncidentReportJpaEntity, Long> {
    List<IncidentReportJpaEntity> findByUserId(Long userId);

    /**
     * Query para encontrar reportes cercanos usando cálculo de distancia.
     * Utiliza la fórmula de Haversine.
     */
    @Query(value = "SELECT * FROM incident_reports WHERE " +
            "( 6371 * acos( cos( radians(:latitude) ) * cos( radians( latitude ) ) * " +
            "cos( radians( longitude ) - radians(:longitude) ) + sin( radians(:latitude) ) * " +
            "sin( radians( latitude ) ) ) ) <= :radiusInKm " +
            "ORDER BY reported_at DESC",
            nativeQuery = true)
    List<IncidentReportJpaEntity> findNearby(@Param("latitude") Double latitude,
                                               @Param("longitude") Double longitude,
                                               @Param("radiusInKm") Double radiusInKm);
}
