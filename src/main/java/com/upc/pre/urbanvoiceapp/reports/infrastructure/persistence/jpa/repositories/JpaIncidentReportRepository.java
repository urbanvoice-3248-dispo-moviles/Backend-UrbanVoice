package com.upc.pre.urbanvoiceapp.reports.infrastructure.persistence.jpa.repositories;

import com.upc.pre.urbanvoiceapp.reports.domain.entities.IncidentReport;
import com.upc.pre.urbanvoiceapp.reports.domain.repositories.IncidentReportRepository;
import com.upc.pre.urbanvoiceapp.reports.infrastructure.persistence.jpa.mappers.IncidentReportJpaMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación de IncidentReportRepository utilizando Spring Data JPA.
 */
@Repository
public class JpaIncidentReportRepository implements IncidentReportRepository {

    private final IncidentReportSpringDataRepository springDataRepository;
    private final IncidentReportJpaMapper mapper;

    public JpaIncidentReportRepository(IncidentReportSpringDataRepository springDataRepository, IncidentReportJpaMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    public IncidentReport save(IncidentReport report) {
        report.validate();
        var jpaEntity = mapper.toJpa(report);
        var savedEntity = springDataRepository.save(jpaEntity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<IncidentReport> findById(Long id) {
        return springDataRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<IncidentReport> findByUserId(Long userId) {
        return springDataRepository.findByUserId(userId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<IncidentReport> findNearby(double latitude, double longitude, double radiusInKm) {
        return springDataRepository.findNearby(latitude, longitude, radiusInKm).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        springDataRepository.deleteById(id);
    }

    @Override
    public List<IncidentReport> findAll() {
        return springDataRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
