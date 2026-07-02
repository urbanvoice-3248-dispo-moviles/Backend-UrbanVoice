package com.upc.pre.urbanvoiceapp.notifications.application.services;

import com.upc.pre.urbanvoiceapp.notifications.domain.entities.Alert;
import com.upc.pre.urbanvoiceapp.notifications.domain.entities.UserAlertConfig;
import com.upc.pre.urbanvoiceapp.notifications.domain.repositories.AlertRepository;
import com.upc.pre.urbanvoiceapp.notifications.domain.repositories.UserAlertConfigRepository;
import com.upc.pre.urbanvoiceapp.notifications.domain.valueobjects.AlertType;
import com.upc.pre.urbanvoiceapp.shared.locationsharing.domain.repositories.UserLiveLocationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@Transactional
public class AlertApplicationService {

    private final AlertRepository alertRepository;
    private final UserAlertConfigRepository configRepository;
    private final UserLiveLocationRepository userLiveLocationRepository;

    public AlertApplicationService(AlertRepository alertRepository,
                                   UserAlertConfigRepository configRepository,
                                   UserLiveLocationRepository userLiveLocationRepository) {
        this.alertRepository = alertRepository;
        this.configRepository = configRepository;
        this.userLiveLocationRepository = userLiveLocationRepository;
    }

    public Alert createAlert(Long userId, String type, String title, String message,
                             Double latitude, Double longitude) {
        return withRetry(() -> {
            AlertType alertType = type != null ? new AlertType(type, type) : AlertType.INFORMATION;
            Alert alert = new Alert(userId, alertType, title, message, latitude, longitude);
            alert.validate();
            return alertRepository.save(alert);
        }, 3);
    }

    @Transactional(readOnly = true)
    public Alert findById(Long id) {
        return alertRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alert not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<Alert> findByUserId(Long userId) {
        Optional<UserAlertConfig> configOpt = configRepository.findByUserId(userId);
        if (configOpt.isPresent() && configOpt.get().isEnabled() && configOpt.get().getRadiusInKm() > 0) {
            Optional<com.upc.pre.urbanvoiceapp.shared.locationsharing.domain.entities.UserLiveLocation> locOpt =
                    userLiveLocationRepository.findByUserId(userId);
            if (locOpt.isPresent()) {
                double userLat = locOpt.get().getLatitude();
                double userLon = locOpt.get().getLongitude();
                double radius = configOpt.get().getRadiusInKm();
                List<Alert> allAlerts = alertRepository.findByUserId(userId);
                return allAlerts.stream()
                        .filter(a -> a.getLatitude() != null && a.getLongitude() != null)
                        .filter(a -> distanceKm(userLat, userLon, a.getLatitude(), a.getLongitude()) <= radius)
                        .collect(Collectors.toList());
            }
        }
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

    public Alert createBroadcast(String title, String message, Double latitude, Double longitude) {
        return withRetry(() -> {
            AlertType alertType = AlertType.INFORMATION;
            Alert alert = new Alert(0L, alertType, title, message, latitude, longitude);
            alert.validate();
            return alertRepository.save(alert);
        }, 3);
    }

    private double distanceKm(double lat1, double lon1, double lat2, double lon2) {
        double r = 6371.0;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return r * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    private <T> T withRetry(Supplier<T> operation, int maxRetries) {
        Exception lastException = null;
        for (int i = 0; i < maxRetries; i++) {
            try {
                return operation.get();
            } catch (Exception e) {
                lastException = e;
                if (i < maxRetries - 1) {
                    try { Thread.sleep(100L * (i + 1)); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
                }
            }
        }
        throw new RuntimeException("Operation failed after " + maxRetries + " retries", lastException);
    }
}
