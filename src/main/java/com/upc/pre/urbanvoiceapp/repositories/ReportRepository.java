package com.upc.pre.urbanvoiceapp.repositories;

import com.upc.pre.urbanvoiceapp.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query(value = "SELECT * FROM reports WHERE id_user = ?1", nativeQuery = true)
    List<Report> findByUserId(Long userId);
}
