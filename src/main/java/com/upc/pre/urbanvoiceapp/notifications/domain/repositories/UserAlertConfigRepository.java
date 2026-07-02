package com.upc.pre.urbanvoiceapp.notifications.domain.repositories;

import com.upc.pre.urbanvoiceapp.notifications.domain.entities.UserAlertConfig;

import java.util.Optional;

public interface UserAlertConfigRepository {
    UserAlertConfig save(UserAlertConfig config);
    Optional<UserAlertConfig> findById(Long id);
    Optional<UserAlertConfig> findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
