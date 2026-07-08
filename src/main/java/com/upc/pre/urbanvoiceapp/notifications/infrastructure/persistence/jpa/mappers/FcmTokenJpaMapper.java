package com.upc.pre.urbanvoiceapp.notifications.infrastructure.persistence.jpa.mappers;

import com.upc.pre.urbanvoiceapp.notifications.domain.entities.FcmToken;
import com.upc.pre.urbanvoiceapp.notifications.infrastructure.persistence.jpa.entities.FcmTokenJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class FcmTokenJpaMapper {

    public FcmToken toDomain(FcmTokenJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        return new FcmToken(
                jpaEntity.getId(),
                jpaEntity.getUserId(),
                jpaEntity.getToken(),
                jpaEntity.getDeviceType(),
                jpaEntity.getCreatedAt(),
                jpaEntity.getUpdatedAt()
        );
    }

    public FcmTokenJpaEntity toJpa(FcmToken domain) {
        if (domain == null) return null;
        FcmTokenJpaEntity jpaEntity = new FcmTokenJpaEntity();
        jpaEntity.setId(domain.getId());
        jpaEntity.setUserId(domain.getUserId());
        jpaEntity.setToken(domain.getToken());
        jpaEntity.setDeviceType(domain.getDeviceType());
        jpaEntity.setCreatedAt(domain.getCreatedAt());
        jpaEntity.setUpdatedAt(domain.getUpdatedAt());
        return jpaEntity;
    }
}
