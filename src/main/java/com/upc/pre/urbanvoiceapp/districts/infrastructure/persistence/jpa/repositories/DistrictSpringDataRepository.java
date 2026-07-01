package com.upc.pre.urbanvoiceapp.districts.infrastructure.persistence.jpa.repositories;

import com.upc.pre.urbanvoiceapp.districts.infrastructure.persistence.jpa.entities.DistrictJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistrictSpringDataRepository extends JpaRepository<DistrictJpaEntity, Long> {
    Optional<DistrictJpaEntity> findByName(String name);
    List<DistrictJpaEntity> findByRiskLevelGreaterThan(int minRiskLevel);
    boolean existsByName(String name);
}
