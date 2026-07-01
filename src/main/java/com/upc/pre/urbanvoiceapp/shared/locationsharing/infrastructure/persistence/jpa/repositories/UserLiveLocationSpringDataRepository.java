package com.upc.pre.urbanvoiceapp.shared.locationsharing.infrastructure.persistence.jpa.repositories;

import com.upc.pre.urbanvoiceapp.shared.locationsharing.infrastructure.persistence.jpa.entities.UserLiveLocationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLiveLocationSpringDataRepository extends JpaRepository<UserLiveLocationJpaEntity, Long> {
    Optional<UserLiveLocationJpaEntity> findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
