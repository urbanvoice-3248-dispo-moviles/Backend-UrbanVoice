package com.upc.pre.urbanvoiceapp.notifications.domain.repositories;

import com.upc.pre.urbanvoiceapp.notifications.domain.entities.Alert;

import java.util.List;
import java.util.Optional;

public interface AlertRepository {
    Alert save(Alert alert);
    Optional<Alert> findById(Long id);
    List<Alert> findByUserId(Long userId);
    List<Alert> findAll();
    void deleteById(Long id);
    void deleteAll();
}
