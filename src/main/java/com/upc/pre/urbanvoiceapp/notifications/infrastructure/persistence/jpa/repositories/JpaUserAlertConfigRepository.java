package com.upc.pre.urbanvoiceapp.notifications.infrastructure.persistence.jpa.repositories;

import com.upc.pre.urbanvoiceapp.notifications.domain.entities.UserAlertConfig;
import com.upc.pre.urbanvoiceapp.notifications.domain.repositories.UserAlertConfigRepository;
import com.upc.pre.urbanvoiceapp.notifications.infrastructure.persistence.jpa.mappers.UserAlertConfigJpaMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaUserAlertConfigRepository implements UserAlertConfigRepository {

    private final UserAlertConfigSpringDataRepository springDataRepository;
    private final UserAlertConfigJpaMapper mapper;

    public JpaUserAlertConfigRepository(UserAlertConfigSpringDataRepository springDataRepository,
                                         UserAlertConfigJpaMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    public UserAlertConfig save(UserAlertConfig config) {
        var jpaEntity = mapper.toJpa(config);
        var saved = springDataRepository.save(jpaEntity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<UserAlertConfig> findById(Long id) {
        return springDataRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<UserAlertConfig> findByUserId(Long userId) {
        return springDataRepository.findByUserId(userId).map(mapper::toDomain);
    }

    @Override
    public void deleteByUserId(Long userId) {
        springDataRepository.deleteByUserId(userId);
    }
}
