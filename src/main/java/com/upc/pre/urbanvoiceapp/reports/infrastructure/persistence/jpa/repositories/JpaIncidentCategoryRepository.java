package com.upc.pre.urbanvoiceapp.reports.infrastructure.persistence.jpa.repositories;

import com.upc.pre.urbanvoiceapp.reports.domain.entities.IncidentCategory;
import com.upc.pre.urbanvoiceapp.reports.domain.repositories.IncidentCategoryRepository;
import com.upc.pre.urbanvoiceapp.reports.infrastructure.persistence.jpa.mappers.IncidentCategoryJpaMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaIncidentCategoryRepository implements IncidentCategoryRepository {

    private final IncidentCategorySpringDataRepository springDataRepository;
    private final IncidentCategoryJpaMapper mapper;

    public JpaIncidentCategoryRepository(IncidentCategorySpringDataRepository springDataRepository,
                                          IncidentCategoryJpaMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    public IncidentCategory save(IncidentCategory category) {
        var jpaEntity = mapper.toJpa(category);
        var saved = springDataRepository.save(jpaEntity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<IncidentCategory> findById(Long id) {
        return springDataRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<IncidentCategory> findAll() {
        return springDataRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        springDataRepository.deleteById(id);
    }
}
