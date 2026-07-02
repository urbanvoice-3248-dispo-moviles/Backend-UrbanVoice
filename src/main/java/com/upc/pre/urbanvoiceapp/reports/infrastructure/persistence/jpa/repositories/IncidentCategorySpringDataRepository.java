package com.upc.pre.urbanvoiceapp.reports.infrastructure.persistence.jpa.repositories;

import com.upc.pre.urbanvoiceapp.reports.infrastructure.persistence.jpa.entities.IncidentCategoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentCategorySpringDataRepository extends JpaRepository<IncidentCategoryJpaEntity, Long> {
}
