package com.upc.pre.urbanvoiceapp.notifications.infrastructure.persistence.jpa.repositories;

import com.upc.pre.urbanvoiceapp.notifications.infrastructure.persistence.jpa.entities.UserAlertConfigJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAlertConfigSpringDataRepository extends JpaRepository<UserAlertConfigJpaEntity, Long> {
    Optional<UserAlertConfigJpaEntity> findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
