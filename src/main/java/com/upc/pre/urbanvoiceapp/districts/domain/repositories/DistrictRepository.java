package com.upc.pre.urbanvoiceapp.districts.domain.repositories;

import com.upc.pre.urbanvoiceapp.districts.domain.entities.District;

import java.util.List;
import java.util.Optional;

public interface DistrictRepository {
    District save(District district);
    Optional<District> findById(Long id);
    Optional<District> findByName(String name);
    List<District> findByRiskLevelGreaterThan(int minRiskLevel);
    List<District> findAll();
    void deleteById(Long id);
    boolean existsByName(String name);
}
