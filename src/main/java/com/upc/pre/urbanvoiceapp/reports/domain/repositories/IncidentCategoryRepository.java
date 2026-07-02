package com.upc.pre.urbanvoiceapp.reports.domain.repositories;

import com.upc.pre.urbanvoiceapp.reports.domain.entities.IncidentCategory;

import java.util.List;
import java.util.Optional;

public interface IncidentCategoryRepository {
    IncidentCategory save(IncidentCategory category);
    Optional<IncidentCategory> findById(Long id);
    List<IncidentCategory> findAll();
    void deleteById(Long id);
}
