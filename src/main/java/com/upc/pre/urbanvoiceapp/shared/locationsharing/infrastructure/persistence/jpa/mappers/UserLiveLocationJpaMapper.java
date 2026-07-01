package com.upc.pre.urbanvoiceapp.shared.locationsharing.infrastructure.persistence.jpa.mappers;

import com.upc.pre.urbanvoiceapp.shared.locationsharing.domain.entities.UserLiveLocation;
import com.upc.pre.urbanvoiceapp.shared.locationsharing.infrastructure.persistence.jpa.entities.UserLiveLocationJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class UserLiveLocationJpaMapper {

    public UserLiveLocation toDomain(UserLiveLocationJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        UserLiveLocation location = new UserLiveLocation(
                jpaEntity.getId(),
                jpaEntity.getUserId(),
                jpaEntity.getLatitude(),
                jpaEntity.getLongitude()
        );
        return location;
    }

    public UserLiveLocationJpaEntity toJpa(UserLiveLocation domain) {
        if (domain == null) return null;
        UserLiveLocationJpaEntity jpaEntity = new UserLiveLocationJpaEntity();
        jpaEntity.setId(domain.getId());
        jpaEntity.setUserId(domain.getUserId());
        jpaEntity.setLatitude(domain.getLatitude());
        jpaEntity.setLongitude(domain.getLongitude());
        jpaEntity.setUpdatedAt(domain.getUpdatedAt());
        return jpaEntity;
    }
}
