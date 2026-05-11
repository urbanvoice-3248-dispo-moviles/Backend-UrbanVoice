package com.upc.pre.urbanvoiceapp.locations.infrastructure.persistence.jpa.repositories;

import com.upc.pre.urbanvoiceapp.locations.domain.entities.Location;
import com.upc.pre.urbanvoiceapp.locations.domain.repositories.LocationRepository;
import com.upc.pre.urbanvoiceapp.locations.infrastructure.persistence.jpa.mappers.LocationJpaMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación de LocationRepository utilizando Spring Data JPA.
 */
@Repository
public class JpaLocationRepository implements LocationRepository {

    private final LocationSpringDataRepository springDataRepository;
    private final LocationJpaMapper mapper;

    public JpaLocationRepository(LocationSpringDataRepository springDataRepository, LocationJpaMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    public Location save(Location location) {
        location.validate();
        var jpaEntity = mapper.toJpa(location);
        var savedEntity = springDataRepository.save(jpaEntity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Location> findById(Long id) {
        return springDataRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Location> findNearby(double latitude, double longitude, double radiusInKm) {
        return springDataRepository.findNearby(latitude, longitude, radiusInKm).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Location> findByDistrict(String district) {
        return springDataRepository.findByDistrict(district).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Location> findByRiskLevelGreaterThan(int minLevel) {
        return springDataRepository.findByRiskLevelGreaterThan(minLevel).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        springDataRepository.deleteById(id);
    }

    @Override
    public List<Location> findAll() {
        return springDataRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
