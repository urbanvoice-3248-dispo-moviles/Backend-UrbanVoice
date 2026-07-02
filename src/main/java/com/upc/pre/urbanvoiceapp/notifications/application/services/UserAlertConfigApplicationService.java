package com.upc.pre.urbanvoiceapp.notifications.application.services;

import com.upc.pre.urbanvoiceapp.notifications.domain.entities.UserAlertConfig;
import com.upc.pre.urbanvoiceapp.notifications.domain.repositories.UserAlertConfigRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserAlertConfigApplicationService {

    private final UserAlertConfigRepository configRepository;

    public UserAlertConfigApplicationService(UserAlertConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public UserAlertConfig getOrCreateConfig(Long userId) {
        return configRepository.findByUserId(userId)
                .orElseGet(() -> configRepository.save(new UserAlertConfig(userId)));
    }

    public UserAlertConfig updateConfig(Long userId, Boolean enabled, Double radiusInKm, Boolean notifyByEmail) {
        UserAlertConfig config = configRepository.findByUserId(userId)
                .orElse(new UserAlertConfig(userId));
        if (enabled != null) config.setEnabled(enabled);
        if (radiusInKm != null) config.setRadiusInKm(radiusInKm);
        if (notifyByEmail != null) config.setNotifyByEmail(notifyByEmail);
        return configRepository.save(config);
    }

    public void deleteConfig(Long userId) {
        configRepository.deleteByUserId(userId);
    }
}
