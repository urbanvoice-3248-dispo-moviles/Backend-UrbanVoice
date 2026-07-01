package com.upc.pre.urbanvoiceapp.shared.locationsharing.infrastructure.persistence.jpa.repositories;

import com.upc.pre.urbanvoiceapp.shared.locationsharing.infrastructure.persistence.jpa.entities.LocationShareSessionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationShareSessionSpringDataRepository extends JpaRepository<LocationShareSessionJpaEntity, Long> {
    List<LocationShareSessionJpaEntity> findByOwnerUserId(Long ownerUserId);
    List<LocationShareSessionJpaEntity> findByTargetUserId(Long targetUserId);
    Optional<LocationShareSessionJpaEntity> findByOwnerUserIdAndTargetUserId(Long ownerUserId, Long targetUserId);
}
