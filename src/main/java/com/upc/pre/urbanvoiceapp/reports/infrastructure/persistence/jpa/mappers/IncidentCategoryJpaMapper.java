package com.upc.pre.urbanvoiceapp.reports.infrastructure.persistence.jpa.mappers;

import com.upc.pre.urbanvoiceapp.reports.domain.entities.IncidentCategory;
import com.upc.pre.urbanvoiceapp.reports.infrastructure.persistence.jpa.entities.IncidentCategoryJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class IncidentCategoryJpaMapper {

    public IncidentCategory toDomain(IncidentCategoryJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        return new IncidentCategory(
                jpaEntity.getId(),
                jpaEntity.getName(),
                jpaEntity.getDescription()
        );
    }

    public IncidentCategoryJpaEntity toJpa(IncidentCategory domain) {
        if (domain == null) return null;
        IncidentCategoryJpaEntity jpaEntity = new IncidentCategoryJpaEntity();
        jpaEntity.setId(domain.getId());
        jpaEntity.setName(domain.getName());
        jpaEntity.setDescription(domain.getDescription());
        return jpaEntity;
    }
}
