package com.upc.pre.urbanvoiceapp.reports.infrastructure.persistence.jpa.repositories;

import com.upc.pre.urbanvoiceapp.reports.infrastructure.persistence.jpa.entities.IncidentReportJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository para operaciones de persistencia de {@link IncidentReportJpaEntity}.
 */
@Repository
public interface IncidentReportSpringDataRepository extends JpaRepository<IncidentReportJpaEntity, Long> {

    /**
     * Busca entidades de reportes creadas por un usuario.
     *
     * @param userId identificador del usuario.
     * @return entidades asociadas al usuario indicado.
     */
    List<IncidentReportJpaEntity> findByUserId(Long userId);

    /**
     * Query para encontrar reportes cercanos usando calculo de distancia.
     * Utiliza la formula de Haversine.
     *
     * @param latitude latitud del punto central.
     * @param longitude longitud del punto central.
     * @param radiusInKm radio maximo en kilometros.
     * @return entidades dentro del radio indicado, ordenadas por fecha descendente.
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
