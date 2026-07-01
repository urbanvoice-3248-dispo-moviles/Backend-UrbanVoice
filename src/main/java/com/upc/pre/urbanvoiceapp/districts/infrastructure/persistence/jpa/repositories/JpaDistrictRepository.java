package com.upc.pre.urbanvoiceapp.districts.infrastructure.persistence.jpa.repositories;

import com.upc.pre.urbanvoiceapp.districts.domain.entities.District;
import com.upc.pre.urbanvoiceapp.districts.domain.repositories.DistrictRepository;
import com.upc.pre.urbanvoiceapp.districts.infrastructure.persistence.jpa.mappers.DistrictJpaMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaDistrictRepository implements DistrictRepository {

    private final DistrictSpringDataRepository springDataRepository;
    private final DistrictJpaMapper mapper;

    public JpaDistrictRepository(DistrictSpringDataRepository springDataRepository, DistrictJpaMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    public District save(District district) {
        district.validate();
        var jpaEntity = mapper.toJpa(district);
        var savedEntity = springDataRepository.save(jpaEntity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<District> findById(Long id) {
        return springDataRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<District> findByName(String name) {
        return springDataRepository.findByName(name).map(mapper::toDomain);
    }

    @Override
    public List<District> findByRiskLevelGreaterThan(int minRiskLevel) {
        return springDataRepository.findByRiskLevelGreaterThan(minRiskLevel).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<District> findAll() {
        return springDataRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        springDataRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return springDataRepository.existsByName(name);
    }
}
