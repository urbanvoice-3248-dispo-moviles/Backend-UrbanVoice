package com.upc.pre.urbanvoiceapp.notifications.infrastructure.persistence.jpa.repositories;

import com.upc.pre.urbanvoiceapp.notifications.infrastructure.persistence.jpa.entities.FcmTokenJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FcmTokenSpringDataRepository extends JpaRepository<FcmTokenJpaEntity, Long> {
    List<FcmTokenJpaEntity> findByUserId(Long userId);
    Optional<FcmTokenJpaEntity> findByToken(String token);
    void deleteByUserId(Long userId);
}
