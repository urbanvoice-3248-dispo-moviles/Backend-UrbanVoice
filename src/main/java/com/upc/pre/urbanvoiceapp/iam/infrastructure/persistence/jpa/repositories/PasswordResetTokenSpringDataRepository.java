package com.upc.pre.urbanvoiceapp.iam.infrastructure.persistence.jpa.repositories;

import com.upc.pre.urbanvoiceapp.iam.infrastructure.persistence.jpa.entities.PasswordResetTokenJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenSpringDataRepository extends JpaRepository<PasswordResetTokenJpaEntity, Long> {
    Optional<PasswordResetTokenJpaEntity> findByToken(String token);
}
