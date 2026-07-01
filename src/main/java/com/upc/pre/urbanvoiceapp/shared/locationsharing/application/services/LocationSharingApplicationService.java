package com.upc.pre.urbanvoiceapp.shared.locationsharing.application.services;

import com.upc.pre.urbanvoiceapp.shared.locationsharing.domain.entities.LocationShareSession;
import com.upc.pre.urbanvoiceapp.shared.locationsharing.domain.entities.UserLiveLocation;
import com.upc.pre.urbanvoiceapp.shared.locationsharing.domain.repositories.LocationShareSessionRepository;
import com.upc.pre.urbanvoiceapp.shared.locationsharing.domain.repositories.UserLiveLocationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class LocationSharingApplicationService {

    private final UserLiveLocationRepository userLiveLocationRepository;
    private final LocationShareSessionRepository shareSessionRepository;

    public LocationSharingApplicationService(
            UserLiveLocationRepository userLiveLocationRepository,
            LocationShareSessionRepository shareSessionRepository) {
        this.userLiveLocationRepository = userLiveLocationRepository;
        this.shareSessionRepository = shareSessionRepository;
    }

    public UserLiveLocation publishLocation(Long userId, Double latitude, Double longitude) {
        UserLiveLocation location = new UserLiveLocation(userId, latitude, longitude);
        return userLiveLocationRepository.save(location);
    }

    @Transactional(readOnly = true)
    public Optional<UserLiveLocation> getMyLocation(Long userId) {
        return userLiveLocationRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<UserLiveLocation> getFriendsLocations(Long userId) {
        List<LocationShareSession> sessionsWhereImTarget =
                shareSessionRepository.findByTargetUserId(userId);
        List<Long> friendIds = sessionsWhereImTarget.stream()
                .filter(LocationShareSession::getActive)
                .map(LocationShareSession::getOwnerUserId)
                .collect(Collectors.toList());
        return userLiveLocationRepository.findAllByIds(friendIds);
    }

    @Transactional(readOnly = true)
    public List<UserLiveLocation> getMySharedLocations(Long userId) {
        List<LocationShareSession> myShares =
                shareSessionRepository.findByOwnerUserId(userId);
        List<Long> targetIds = myShares.stream()
                .filter(LocationShareSession::getActive)
                .map(LocationShareSession::getTargetUserId)
                .collect(Collectors.toList());
        return userLiveLocationRepository.findAllByIds(targetIds);
    }

    public LocationShareSession startSharing(Long ownerUserId, Long targetUserId) {
        Optional<LocationShareSession> existing =
                shareSessionRepository.findByOwnerAndTarget(ownerUserId, targetUserId);
        if (existing.isPresent()) {
            LocationShareSession session = existing.get();
            if (!session.getActive()) {
                session.activate();
                return shareSessionRepository.save(session);
            }
            return session;
        }
        LocationShareSession session = new LocationShareSession(ownerUserId, targetUserId);
        return shareSessionRepository.save(session);
    }

    public void stopSharing(Long ownerUserId, Long targetUserId) {
        shareSessionRepository.findByOwnerAndTarget(ownerUserId, targetUserId)
                .ifPresent(session -> {
                    session.deactivate();
                    shareSessionRepository.save(session);
                });
    }

    @Transactional(readOnly = true)
    public List<LocationShareSession> getMyShares(Long userId) {
        return shareSessionRepository.findByOwnerUserId(userId).stream()
                .filter(LocationShareSession::getActive)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LocationShareSession> getSharesWithMe(Long userId) {
        return shareSessionRepository.findByTargetUserId(userId).stream()
                .filter(LocationShareSession::getActive)
                .collect(Collectors.toList());
    }
}
