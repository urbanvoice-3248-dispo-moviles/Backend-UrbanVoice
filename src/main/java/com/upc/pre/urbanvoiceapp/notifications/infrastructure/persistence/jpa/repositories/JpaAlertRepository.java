package com.upc.pre.urbanvoiceapp.notifications.infrastructure.persistence.jpa.repositories;

import com.upc.pre.urbanvoiceapp.notifications.domain.entities.Alert;
import com.upc.pre.urbanvoiceapp.notifications.domain.repositories.AlertRepository;
import com.upc.pre.urbanvoiceapp.notifications.infrastructure.persistence.jpa.mappers.AlertJpaMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaAlertRepository implements AlertRepository {

    private final AlertSpringDataRepository springDataRepository;
    private final AlertJpaMapper mapper;

    public JpaAlertRepository(AlertSpringDataRepository springDataRepository, AlertJpaMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    public Alert save(Alert alert) {
        var jpaEntity = mapper.toJpa(alert);
        var saved = springDataRepository.save(jpaEntity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Alert> findById(Long id) {
        return springDataRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Alert> findByUserId(Long userId) {
        return springDataRepository.findByUserId(userId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Alert> findAll() {
        return springDataRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        springDataRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        springDataRepository.deleteAll();
    }
}
