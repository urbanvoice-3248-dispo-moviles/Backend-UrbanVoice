package com.upc.pre.urbanvoiceapp.notifications.infrastructure.persistence.jpa.mappers;

import com.upc.pre.urbanvoiceapp.notifications.domain.entities.Alert;
import com.upc.pre.urbanvoiceapp.notifications.domain.valueobjects.AlertType;
import com.upc.pre.urbanvoiceapp.notifications.infrastructure.persistence.jpa.entities.AlertJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class AlertJpaMapper {

    public Alert toDomain(AlertJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;

        AlertType alertType = jpaEntity.getAlertType() != null
                ? new AlertType(jpaEntity.getAlertType(), jpaEntity.getAlertType())
                : null;

        Alert alert = new Alert(
                jpaEntity.getId(),
                jpaEntity.getUserId(),
                alertType,
                jpaEntity.getTitle(),
                jpaEntity.getMessage(),
                jpaEntity.getLatitude(),
                jpaEntity.getLongitude()
        );

        alert.setIsRead(jpaEntity.getIsRead() != null && jpaEntity.getIsRead());
        return alert;
    }

    public AlertJpaEntity toJpa(Alert domain) {
        if (domain == null) return null;

        AlertJpaEntity jpaEntity = new AlertJpaEntity();
        jpaEntity.setId(domain.getId());
        jpaEntity.setUserId(domain.getUserId());
        jpaEntity.setAlertType(domain.getAlertType() != null ? domain.getAlertType().getType() : null);
        jpaEntity.setTitle(domain.getTitle());
        jpaEntity.setMessage(domain.getMessage());
        jpaEntity.setLatitude(domain.getLatitude());
        jpaEntity.setLongitude(domain.getLongitude());
        jpaEntity.setIsRead(domain.getIsRead());
        jpaEntity.setCreatedAt(domain.getCreatedAt());

        return jpaEntity;
    }
}
