package com.upc.pre.urbanvoiceapp.reports.infrastructure.persistence.jpa.mappers;

import com.upc.pre.urbanvoiceapp.reports.domain.entities.IncidentReport;
import com.upc.pre.urbanvoiceapp.reports.domain.valueobjects.GeoLocation;
import com.upc.pre.urbanvoiceapp.reports.domain.valueobjects.IncidentType;
import com.upc.pre.urbanvoiceapp.reports.infrastructure.persistence.jpa.entities.IncidentReportJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre el modelo de Dominio {@link IncidentReport} y la entidad JPA.
 *
 * <p>Mantiene aislado al dominio de las anotaciones y convenciones de
 * persistencia usadas por Spring Data JPA.</p>
 */
@Component
public class IncidentReportJpaMapper {

    /**
     * Convierte una entidad JPA en agregado de dominio.
     *
     * @param jpaEntity entidad persistida; puede ser {@code null}.
     * @return agregado de dominio equivalente, o {@code null} si la entrada es {@code null}.
     */
    public IncidentReport toDomain(IncidentReportJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }

        GeoLocation location = new GeoLocation(
                jpaEntity.getLatitude(),
                jpaEntity.getLongitude(),
                jpaEntity.getAddress()
        );

        IncidentType incidentType = new IncidentType(
                jpaEntity.getIncidentType(),
                jpaEntity.getIncidentType()
        );

        IncidentReport report = new IncidentReport(
                jpaEntity.getId(),
                jpaEntity.getUserId(),
                incidentType,
                location,
                jpaEntity.getTitle(),
                jpaEntity.getDescription(),
                jpaEntity.getMediaUrl(),
                jpaEntity.getIsAnonymous()
        );

        report.setReportedAt(jpaEntity.getReportedAt());

        return report;
    }

    /**
     * Convierte un agregado de dominio en entidad JPA.
     *
     * @param domain agregado de dominio; puede ser {@code null}.
     * @return entidad persistible equivalente, o {@code null} si la entrada es {@code null}.
     */
    public IncidentReportJpaEntity toJpa(IncidentReport domain) {
        if (domain == null) {
            return null;
        }

        IncidentReportJpaEntity jpaEntity = new IncidentReportJpaEntity();
        jpaEntity.setId(domain.getId());
        jpaEntity.setUserId(domain.getUserId());
        jpaEntity.setTitle(domain.getTitle());
        jpaEntity.setDescription(domain.getDescription());
        jpaEntity.setIncidentType(domain.getIncidentType().getType());
        jpaEntity.setLatitude(domain.getLocation().getLatitude());
        jpaEntity.setLongitude(domain.getLocation().getLongitude());
        jpaEntity.setAddress(domain.getLocation().getAddress());
        jpaEntity.setMediaUrl(domain.getMediaUrl());
        jpaEntity.setIsAnonymous(domain.isAnonymous());
        jpaEntity.setReportedAt(domain.getReportedAt());

        return jpaEntity;
    }
}
