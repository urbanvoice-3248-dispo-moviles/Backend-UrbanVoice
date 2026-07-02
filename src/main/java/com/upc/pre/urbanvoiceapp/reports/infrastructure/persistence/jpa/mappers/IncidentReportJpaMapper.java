package com.upc.pre.urbanvoiceapp.reports.infrastructure.persistence.jpa.mappers;

import com.upc.pre.urbanvoiceapp.reports.domain.entities.IncidentReport;
import com.upc.pre.urbanvoiceapp.reports.domain.valueobjects.GeoLocation;
import com.upc.pre.urbanvoiceapp.reports.domain.valueobjects.IncidentType;
import com.upc.pre.urbanvoiceapp.reports.infrastructure.persistence.jpa.entities.IncidentReportJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre el modelo de Dominio (IncidentReport) y la entidad JPA.
 */
@Component
public class IncidentReportJpaMapper {

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
        report.setStatus(jpaEntity.getStatus());

        return report;
    }

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
        jpaEntity.setStatus(domain.getStatus());
        jpaEntity.setReportedAt(domain.getReportedAt());

        return jpaEntity;
    }
}
