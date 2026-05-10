package com.upc.pre.urbanvoiceapp.profiles.infrastructure.persistence.jpa.repositories;

import com.upc.pre.urbanvoiceapp.profiles.domain.entities.UserProfile;
import com.upc.pre.urbanvoiceapp.profiles.domain.repositories.UserProfileRepository;
import com.upc.pre.urbanvoiceapp.profiles.infrastructure.persistence.jpa.mappers.UserProfileJpaMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación de UserProfileRepository utilizando Spring Data JPA.
 * Actúa como adapter entre el dominio y la persistencia.
 */
@Repository
public class JpaUserProfileRepository implements UserProfileRepository {

    private final UserProfileSpringDataRepository springDataRepository;
    private final UserProfileJpaMapper mapper;

    public JpaUserProfileRepository(UserProfileSpringDataRepository springDataRepository, UserProfileJpaMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    public UserProfile save(UserProfile userProfile) {
        userProfile.validate();
        var jpaEntity = mapper.toJpa(userProfile);
        var savedEntity = springDataRepository.save(jpaEntity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<UserProfile> findById(Long id) {
        return springDataRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<UserProfile> findByEmail(String email) {
        return springDataRepository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return springDataRepository.existsByEmail(email);
    }

    @Override
    public void deleteById(Long id) {
        springDataRepository.deleteById(id);
    }

    @Override
    public Iterable<UserProfile> findAll() {
        return springDataRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
