package com.upc.pre.urbanvoiceapp.profiles.infrastructure.persistence.jpa.mappers;

import com.upc.pre.urbanvoiceapp.profiles.domain.entities.UserProfile;
import com.upc.pre.urbanvoiceapp.profiles.domain.valueobjects.ContactInfo;
import com.upc.pre.urbanvoiceapp.profiles.domain.valueobjects.PersonalInfo;
import com.upc.pre.urbanvoiceapp.profiles.infrastructure.persistence.jpa.entities.UserProfileJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre el modelo de Dominio (UserProfile) y la entidad JPA (UserProfileJpaEntity).
 */
@Component
public class UserProfileJpaMapper {

    public UserProfile toDomain(UserProfileJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }

        PersonalInfo personalInfo = new PersonalInfo(
                jpaEntity.getName(),
                jpaEntity.getLastName(),
                jpaEntity.getAge() != null ? jpaEntity.getAge() : 0
        );

        ContactInfo contactInfo = new ContactInfo(
                jpaEntity.getEmail(),
                jpaEntity.getPhoneNumber()
        );

        UserProfile userProfile = new UserProfile(
                jpaEntity.getId(),
                personalInfo,
                contactInfo,
                jpaEntity.getProfileImageUrl()
        );

        return userProfile;
    }

    public UserProfileJpaEntity toJpa(UserProfile domain) {
        if (domain == null) {
            return null;
        }

        UserProfileJpaEntity jpaEntity = new UserProfileJpaEntity();
        jpaEntity.setId(domain.getId());
        jpaEntity.setName(domain.getPersonalInfo().getName());
        jpaEntity.setLastName(domain.getPersonalInfo().getLastName());
        jpaEntity.setAge(domain.getPersonalInfo().getAge());
        jpaEntity.setEmail(domain.getContactInfo().getEmail());
        jpaEntity.setPhoneNumber(domain.getContactInfo().getPhoneNumber());
        jpaEntity.setProfileImageUrl(domain.getProfileImageUrl());
        jpaEntity.setCreatedAt(domain.getCreatedAt());
        jpaEntity.setUpdatedAt(domain.getUpdatedAt());

        return jpaEntity;
    }
}
