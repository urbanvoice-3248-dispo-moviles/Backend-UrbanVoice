package com.upc.pre.urbanvoiceapp.profiles.application.services;

import com.upc.pre.urbanvoiceapp.profiles.application.commands.CreateUserProfileCommand;
import com.upc.pre.urbanvoiceapp.profiles.application.commands.DeleteUserProfileCommand;
import com.upc.pre.urbanvoiceapp.profiles.application.commands.UpdateUserProfileCommand;
import com.upc.pre.urbanvoiceapp.profiles.application.queries.GetUserProfileByEmailQuery;
import com.upc.pre.urbanvoiceapp.profiles.application.queries.GetUserProfileByIdQuery;
import com.upc.pre.urbanvoiceapp.profiles.domain.entities.UserProfile;
import com.upc.pre.urbanvoiceapp.profiles.domain.exceptions.DuplicateUserProfileException;
import com.upc.pre.urbanvoiceapp.profiles.domain.exceptions.UserProfileNotFoundException;
import com.upc.pre.urbanvoiceapp.profiles.domain.repositories.UserProfileRepository;
import com.upc.pre.urbanvoiceapp.profiles.domain.valueobjects.ContactInfo;
import com.upc.pre.urbanvoiceapp.profiles.domain.valueobjects.PersonalInfo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Application Service para orquestar operaciones sobre perfiles de usuario.
 * Orquesta entre controladores REST y la lógica de dominio.
 */
@Service
@Transactional
public class UserProfileApplicationService {

    private final UserProfileRepository userProfileRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserProfileApplicationService(UserProfileRepository userProfileRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Maneja la creación de un nuevo perfil de usuario.
     * Valida que el email sea único antes de crear el perfil.
     */
    public UserProfile handle(CreateUserProfileCommand command) {
        // Validar que el email no exista ya
        if (userProfileRepository.existsByEmail(command.getEmail())) {
            throw new DuplicateUserProfileException(command.getEmail());
        }

        // Crear value objects de dominio
        PersonalInfo personalInfo = new PersonalInfo(
                command.getName(),
                command.getLastName(),
                command.getAge()
        );

        ContactInfo contactInfo = new ContactInfo(
                command.getEmail(),
                command.getPhoneNumber()
        );

        // Crear el agregado de dominio
        UserProfile userProfile = new UserProfile(personalInfo, contactInfo, command.getProfileImageUrl());
        userProfile.setPassword(passwordEncoder.encode(command.getPassword()));
        userProfile.validate();
        
        // Persistir usando el repositorio
        return userProfileRepository.save(userProfile);
    }

    /**
     * Maneja la actualización de un perfil existente.
     * Valida que el usuario exista antes de actualizarlo.
     */
    public UserProfile handle(UpdateUserProfileCommand command) {
        UserProfile userProfile = userProfileRepository.findById(command.getUserId())
                .orElseThrow(() -> new UserProfileNotFoundException(command.getUserId()));

        // Actualizar información personal si se proporciona
        if (command.getName() != null || command.getLastName() != null || command.getAge() > 0) {
            PersonalInfo newPersonalInfo = new PersonalInfo(
                    command.getName() != null ? command.getName() : userProfile.getPersonalInfo().getName(),
                    command.getLastName() != null ? command.getLastName() : userProfile.getPersonalInfo().getLastName(),
                    command.getAge() > 0 ? command.getAge() : userProfile.getPersonalInfo().getAge()
            );
            userProfile.updatePersonalInfo(newPersonalInfo);
        }

        // Actualizar información de contacto si se proporciona
        if (command.getPhoneNumber() != null) {
            ContactInfo newContactInfo = new ContactInfo(
                    userProfile.getContactInfo().getEmail(),
                    command.getPhoneNumber()
            );
            userProfile.updateContactInfo(newContactInfo);
        }

        // Actualizar imagen de perfil si se proporciona
        if (command.getProfileImageUrl() != null) {
            userProfile.updateProfileImage(command.getProfileImageUrl());
        }

        userProfile.validate();
        return userProfileRepository.save(userProfile);
    }

    /**
     * Maneja la eliminación de un perfil.
     * Valida que el usuario exista antes de eliminarlo.
     */
    public void handle(DeleteUserProfileCommand command) {
        UserProfile userProfile = userProfileRepository.findById(command.getUserId())
                .orElseThrow(() -> new UserProfileNotFoundException(command.getUserId()));
        
        userProfileRepository.deleteById(command.getUserId());
    }

    /**
     * Maneja la consulta de un perfil por ID.
     */
    @Transactional(readOnly = true)
    public UserProfile handle(GetUserProfileByIdQuery query) {
        return userProfileRepository.findById(query.getUserId())
                .orElseThrow(() -> new UserProfileNotFoundException(query.getUserId()));
    }

    /**
     * Maneja la consulta de un perfil por email.
     */
    @Transactional(readOnly = true)
    public UserProfile handle(GetUserProfileByEmailQuery query) {
        return userProfileRepository.findByEmail(query.getEmail())
                .orElseThrow(() -> new UserProfileNotFoundException(query.getEmail()));
    }
}

