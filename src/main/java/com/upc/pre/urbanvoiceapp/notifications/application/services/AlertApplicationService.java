package com.upc.pre.urbanvoiceapp.notifications.application.services;

import com.upc.pre.urbanvoiceapp.notifications.domain.entities.Alert;
import com.upc.pre.urbanvoiceapp.notifications.domain.repositories.AlertRepository;
import com.upc.pre.urbanvoiceapp.notifications.domain.valueobjects.AlertType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AlertApplicationService {

    private final AlertRepository alertRepository;

    public AlertApplicationService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public Alert createAlert(Long userId, String type, String title, String message,
                             Double latitude, Double longitude) {
        AlertType alertType = type != null ? new AlertType(type, type) : AlertType.INFORMATION;
        Alert alert = new Alert(userId, alertType, title, message, latitude, longitude);
        alert.validate();
        return alertRepository.save(alert);
    }

    @Transactional(readOnly = true)
    public Alert findById(Long id) {
        return alertRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alert not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<Alert> findByUserId(Long userId) {
        return alertRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Alert> findAll() {
        return alertRepository.findAll();
    }

    public void deleteById(Long id) {
        alertRepository.deleteById(id);
    }

    public void deleteAll() {
        alertRepository.deleteAll();
    }
}
