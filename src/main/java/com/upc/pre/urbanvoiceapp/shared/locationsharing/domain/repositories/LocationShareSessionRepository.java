package com.upc.pre.urbanvoiceapp.shared.locationsharing.domain.repositories;

import com.upc.pre.urbanvoiceapp.shared.locationsharing.domain.entities.LocationShareSession;

import java.util.List;
import java.util.Optional;

public interface LocationShareSessionRepository {
    LocationShareSession save(LocationShareSession session);
    Optional<LocationShareSession> findById(Long id);
    List<LocationShareSession> findByOwnerUserId(Long ownerUserId);
    List<LocationShareSession> findByTargetUserId(Long targetUserId);
    Optional<LocationShareSession> findByOwnerAndTarget(Long ownerUserId, Long targetUserId);
    void deleteById(Long id);
}
