package com.upc.pre.urbanvoiceapp.notifications.domain.repositories;

import com.upc.pre.urbanvoiceapp.notifications.domain.entities.FcmToken;

import java.util.List;
import java.util.Optional;

public interface FcmTokenRepository {
    FcmToken save(FcmToken token);
    Optional<FcmToken> findById(Long id);
    List<FcmToken> findByUserId(Long userId);
    Optional<FcmToken> findByToken(String token);
    void deleteById(Long id);
    void deleteByUserId(Long userId);
}
