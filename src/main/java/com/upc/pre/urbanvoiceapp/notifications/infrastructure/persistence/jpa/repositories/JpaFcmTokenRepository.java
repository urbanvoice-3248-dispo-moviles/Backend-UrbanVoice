package com.upc.pre.urbanvoiceapp.notifications.infrastructure.persistence.jpa.repositories;

import com.upc.pre.urbanvoiceapp.notifications.domain.entities.FcmToken;
import com.upc.pre.urbanvoiceapp.notifications.domain.repositories.FcmTokenRepository;
import com.upc.pre.urbanvoiceapp.notifications.infrastructure.persistence.jpa.mappers.FcmTokenJpaMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaFcmTokenRepository implements FcmTokenRepository {

    private final FcmTokenSpringDataRepository springDataRepository;
    private final FcmTokenJpaMapper mapper;

    public JpaFcmTokenRepository(FcmTokenSpringDataRepository springDataRepository,
                                 FcmTokenJpaMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    public FcmToken save(FcmToken token) {
        var jpaEntity = mapper.toJpa(token);
        var saved = springDataRepository.save(jpaEntity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<FcmToken> findById(Long id) {
        return springDataRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<FcmToken> findByUserId(Long userId) {
        return springDataRepository.findByUserId(userId)
                .stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<FcmToken> findByToken(String token) {
        return springDataRepository.findByToken(token).map(mapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        springDataRepository.deleteById(id);
    }

    @Override
    public void deleteByUserId(Long userId) {
        springDataRepository.deleteByUserId(userId);
    }
}
