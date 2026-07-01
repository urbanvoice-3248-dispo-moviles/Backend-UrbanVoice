package com.upc.pre.urbanvoiceapp.shared.locationsharing.infrastructure.persistence.jpa.repositories;

import com.upc.pre.urbanvoiceapp.shared.locationsharing.domain.entities.LocationShareSession;
import com.upc.pre.urbanvoiceapp.shared.locationsharing.domain.repositories.LocationShareSessionRepository;
import com.upc.pre.urbanvoiceapp.shared.locationsharing.infrastructure.persistence.jpa.mappers.LocationShareSessionJpaMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaLocationShareSessionRepository implements LocationShareSessionRepository {

    private final LocationShareSessionSpringDataRepository springDataRepository;
    private final LocationShareSessionJpaMapper mapper;

    public JpaLocationShareSessionRepository(
            LocationShareSessionSpringDataRepository springDataRepository,
            LocationShareSessionJpaMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    public LocationShareSession save(LocationShareSession session) {
        session.validate();
        var jpaEntity = mapper.toJpa(session);
        var saved = springDataRepository.save(jpaEntity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<LocationShareSession> findById(Long id) {
        return springDataRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<LocationShareSession> findByOwnerUserId(Long ownerUserId) {
        return springDataRepository.findByOwnerUserId(ownerUserId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<LocationShareSession> findByTargetUserId(Long targetUserId) {
        return springDataRepository.findByTargetUserId(targetUserId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LocationShareSession> findByOwnerAndTarget(Long ownerUserId, Long targetUserId) {
        return springDataRepository.findByOwnerUserIdAndTargetUserId(ownerUserId, targetUserId)
                .map(mapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        springDataRepository.deleteById(id);
    }
}
