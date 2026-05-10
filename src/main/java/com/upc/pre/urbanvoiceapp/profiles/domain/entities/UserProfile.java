package com.upc.pre.urbanvoiceapp.profiles.domain.entities;

import com.upc.pre.urbanvoiceapp.profiles.domain.events.UserProfileCreatedEvent;
import com.upc.pre.urbanvoiceapp.profiles.domain.events.UserProfileUpdatedEvent;
import com.upc.pre.urbanvoiceapp.profiles.domain.valueobjects.ContactInfo;
import com.upc.pre.urbanvoiceapp.profiles.domain.valueobjects.PersonalInfo;
import com.upc.pre.urbanvoiceapp.shared.domain.events.DomainEvent;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Aggregate Root del contexto Profile Management.
 * Representa el perfil de un usuario ciudadano de UrbanVoice.
 */
@Getter
public class UserProfile {
    private final Long id;
    private PersonalInfo personalInfo;
    private ContactInfo contactInfo;
    private String profileImageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public UserProfile(Long id, PersonalInfo personalInfo, ContactInfo contactInfo, String profileImageUrl) {
        this.id = id;
        this.personalInfo = personalInfo;
        this.contactInfo = contactInfo;
        this.profileImageUrl = profileImageUrl;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Constructor para crear un nuevo UserProfile (sin ID)
     */
    public UserProfile(PersonalInfo personalInfo, ContactInfo contactInfo, String profileImageUrl) {
        this(null, personalInfo, contactInfo, profileImageUrl);
    }

    /**
     * Actualiza la información personal del usuario.
     */
    public void updatePersonalInfo(PersonalInfo newPersonalInfo) {
        if (newPersonalInfo == null) {
            throw new IllegalArgumentException("Personal info cannot be null");
        }
        this.personalInfo = newPersonalInfo;
        this.updatedAt = LocalDateTime.now();
        this.domainEvents.add(new UserProfileUpdatedEvent(this.id, newPersonalInfo.getFullName(), contactInfo.getEmail()));
    }

    /**
     * Actualiza la información de contacto del usuario.
     */
    public void updateContactInfo(ContactInfo newContactInfo) {
        if (newContactInfo == null) {
            throw new IllegalArgumentException("Contact info cannot be null");
        }
        this.contactInfo = newContactInfo;
        this.updatedAt = LocalDateTime.now();
        this.domainEvents.add(new UserProfileUpdatedEvent(this.id, personalInfo.getFullName(), newContactInfo.getEmail()));
    }

    /**
     * Actualiza la imagen de perfil del usuario.
     */
    public void updateProfileImage(String imageUrl) {
        this.profileImageUrl = imageUrl;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Registra un evento de creación cuando se persiste por primera vez.
     */
    public void recordCreation() {
        this.domainEvents.add(new UserProfileCreatedEvent(this.id, contactInfo.getEmail(), personalInfo.getFullName()));
    }

    /**
     * Obtiene y limpia los eventos registrados.
     */
    public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> events = new ArrayList<>(domainEvents);
        domainEvents.clear();
        return events;
    }

    /**
     * Valida el estado del agregado.
     */
    public void validate() {
        if (personalInfo == null || contactInfo == null) {
            throw new IllegalStateException("UserProfile must have personalInfo and contactInfo");
        }
    }
}
