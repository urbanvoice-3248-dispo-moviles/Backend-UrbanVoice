package com.upc.pre.urbanvoiceapp.notifications.infrastructure.persistence.jpa.mappers;

import com.upc.pre.urbanvoiceapp.notifications.domain.entities.UserAlertConfig;
import com.upc.pre.urbanvoiceapp.notifications.infrastructure.persistence.jpa.entities.UserAlertConfigJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class UserAlertConfigJpaMapper {

    public UserAlertConfig toDomain(UserAlertConfigJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        return new UserAlertConfig(
                jpaEntity.getId(),
                jpaEntity.getUserId(),
                jpaEntity.isEnabled(),
                jpaEntity.getRadiusInKm(),
                jpaEntity.isNotifyByEmail()
        );
    }

    public UserAlertConfigJpaEntity toJpa(UserAlertConfig domain) {
        if (domain == null) return null;
        UserAlertConfigJpaEntity jpaEntity = new UserAlertConfigJpaEntity();
        jpaEntity.setId(domain.getId());
        jpaEntity.setUserId(domain.getUserId());
        jpaEntity.setEnabled(domain.isEnabled());
        jpaEntity.setRadiusInKm(domain.getRadiusInKm());
        jpaEntity.setNotifyByEmail(domain.isNotifyByEmail());
        return jpaEntity;
    }
}
