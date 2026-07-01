package com.upc.pre.urbanvoiceapp.shared.locationsharing.infrastructure.persistence.jpa.repositories;

import com.upc.pre.urbanvoiceapp.shared.locationsharing.domain.entities.UserLiveLocation;
import com.upc.pre.urbanvoiceapp.shared.locationsharing.domain.repositories.UserLiveLocationRepository;
import com.upc.pre.urbanvoiceapp.shared.locationsharing.infrastructure.persistence.jpa.mappers.UserLiveLocationJpaMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaUserLiveLocationRepository implements UserLiveLocationRepository {

    private final UserLiveLocationSpringDataRepository springDataRepository;
    private final UserLiveLocationJpaMapper mapper;

    public JpaUserLiveLocationRepository(
            UserLiveLocationSpringDataRepository springDataRepository,
            UserLiveLocationJpaMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    public UserLiveLocation save(UserLiveLocation location) {
        location.validate();
        var existing = springDataRepository.findByUserId(location.getUserId());
        if (existing.isPresent()) {
            var jpaEntity = existing.get();
            jpaEntity.setLatitude(location.getLatitude());
            jpaEntity.setLongitude(location.getLongitude());
            var saved = springDataRepository.save(jpaEntity);
            return mapper.toDomain(saved);
        }
        var jpaEntity = mapper.toJpa(location);
        var saved = springDataRepository.save(jpaEntity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<UserLiveLocation> findByUserId(Long userId) {
        return springDataRepository.findByUserId(userId).map(mapper::toDomain);
    }

    @Override
    public List<UserLiveLocation> findAllByIds(List<Long> userIds) {
        return springDataRepository.findAllById(userIds).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByUserId(Long userId) {
        springDataRepository.deleteByUserId(userId);
    }
}
