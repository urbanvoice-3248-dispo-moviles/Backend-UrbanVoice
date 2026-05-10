package com.upc.pre.urbanvoiceapp.profiles.domain.repositories;

import com.upc.pre.urbanvoiceapp.profiles.domain.entities.UserProfile;

import java.util.Optional;

/**
 * Repository interface para el Aggregate Root UserProfile.
 * Define los métodos para persistencia que deben ser implementados por la capa de Infraestructura.
 */
public interface UserProfileRepository {
    
    /**
     * Guarda un nuevo UserProfile o actualiza uno existente.
     */
    UserProfile save(UserProfile userProfile);

    /**
     * Busca un UserProfile por su ID.
     */
    Optional<UserProfile> findById(Long id);

    /**
     * Busca un UserProfile por su email.
     */
    Optional<UserProfile> findByEmail(String email);

    /**
     * Verifica si existe un UserProfile con el email especificado.
     */
    boolean existsByEmail(String email);

    /**
     * Elimina un UserProfile por su ID.
     */
    void deleteById(Long id);

    /**
     * Obtiene todos los UserProfiles (para consultas).
     */
    Iterable<UserProfile> findAll();
}
