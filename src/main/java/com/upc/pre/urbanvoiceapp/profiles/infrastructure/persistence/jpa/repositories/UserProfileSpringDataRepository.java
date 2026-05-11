package com.upc.pre.urbanvoiceapp.profiles.infrastructure.persistence.jpa.repositories;

import com.upc.pre.urbanvoiceapp.profiles.infrastructure.persistence.jpa.entities.UserProfileJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA Repository para operaciones de persistencia de UserProfileJpaEntity.
 */
@Repository
public interface UserProfileSpringDataRepository extends JpaRepository<UserProfileJpaEntity, Long> {
    Optional<UserProfileJpaEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
