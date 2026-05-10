package com.upc.pre.urbanvoiceapp.repositories;

import com.upc.pre.urbanvoiceapp.models.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> { // Changed Integer to Long
    @Query(value = "SELECT * FROM alerts WHERE id_user = ?1", nativeQuery = true)
    List<Alert> findByUserId(int userId);
}
