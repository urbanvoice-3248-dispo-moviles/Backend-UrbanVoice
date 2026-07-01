package com.upc.pre.urbanvoiceapp.shared.locationsharing.infrastructure.persistence.jpa.mappers;

import com.upc.pre.urbanvoiceapp.shared.locationsharing.domain.entities.LocationShareSession;
import com.upc.pre.urbanvoiceapp.shared.locationsharing.infrastructure.persistence.jpa.entities.LocationShareSessionJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class LocationShareSessionJpaMapper {

    public LocationShareSession toDomain(LocationShareSessionJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        LocationShareSession session = new LocationShareSession(
                jpaEntity.getId(),
                jpaEntity.getOwnerUserId(),
                jpaEntity.getTargetUserId()
        );
        if (!jpaEntity.getActive()) {
            session.deactivate();
        }
        return session;
    }

    public LocationShareSessionJpaEntity toJpa(LocationShareSession domain) {
        if (domain == null) return null;
        LocationShareSessionJpaEntity jpaEntity = new LocationShareSessionJpaEntity();
        jpaEntity.setId(domain.getId());
        jpaEntity.setOwnerUserId(domain.getOwnerUserId());
        jpaEntity.setTargetUserId(domain.getTargetUserId());
        jpaEntity.setActive(domain.getActive());
        jpaEntity.setCreatedAt(domain.getCreatedAt());
        return jpaEntity;
    }
}
