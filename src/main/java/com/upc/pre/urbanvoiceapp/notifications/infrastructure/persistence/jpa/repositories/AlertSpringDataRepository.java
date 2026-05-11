package com.upc.pre.urbanvoiceapp.notifications.infrastructure.persistence.jpa.repositories;

import com.upc.pre.urbanvoiceapp.notifications.infrastructure.persistence.jpa.entities.AlertJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertSpringDataRepository extends JpaRepository<AlertJpaEntity, Long> {
    List<AlertJpaEntity> findByUserId(Long userId);
}
