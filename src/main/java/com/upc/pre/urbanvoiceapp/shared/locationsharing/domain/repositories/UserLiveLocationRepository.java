package com.upc.pre.urbanvoiceapp.shared.locationsharing.domain.repositories;

import com.upc.pre.urbanvoiceapp.shared.locationsharing.domain.entities.UserLiveLocation;

import java.util.List;
import java.util.Optional;

public interface UserLiveLocationRepository {
    UserLiveLocation save(UserLiveLocation location);
    Optional<UserLiveLocation> findByUserId(Long userId);
    List<UserLiveLocation> findAllByIds(List<Long> userIds);
    void deleteByUserId(Long userId);
}
