package com.upc.pre.urbanvoiceapp.profiles.interfaces.rest.assemblers;

import com.upc.pre.urbanvoiceapp.profiles.application.commands.CreateUserProfileCommand;
import com.upc.pre.urbanvoiceapp.profiles.application.commands.UpdateUserProfileCommand;
import com.upc.pre.urbanvoiceapp.profiles.domain.entities.UserProfile;
import com.upc.pre.urbanvoiceapp.profiles.domain.valueobjects.ContactInfo;
import com.upc.pre.urbanvoiceapp.profiles.domain.valueobjects.PersonalInfo;
import com.upc.pre.urbanvoiceapp.profiles.interfaces.rest.resources.CreateUserProfileResource;
import com.upc.pre.urbanvoiceapp.profiles.interfaces.rest.resources.UpdateUserProfileResource;
import com.upc.pre.urbanvoiceapp.profiles.interfaces.rest.resources.UserProfileResponse;
import org.springframework.stereotype.Component;

/**
 * Assembler para convertir entre DTOs (Resources) y Commands/Domain Models.
 */
@Component
public class UserProfileAssembler {

    public CreateUserProfileCommand toCreateCommand(CreateUserProfileResource resource) {
        return new CreateUserProfileCommand(
                resource.getName(),
                resource.getLastName(),
                resource.getAge() != null ? resource.getAge() : 0,
                resource.getEmail(),
                resource.getPhoneNumber(),
                resource.getProfileImageUrl()
        );
    }

    public UpdateUserProfileCommand toUpdateCommand(Long userId, UpdateUserProfileResource resource) {
        return new UpdateUserProfileCommand(
                userId,
                resource.getName(),
                resource.getLastName(),
                resource.getAge() != null ? resource.getAge() : 0,
                resource.getPhoneNumber(),
                resource.getProfileImageUrl()
        );
    }

    public UserProfileResponse toResponse(UserProfile userProfile) {
        UserProfileResponse response = new UserProfileResponse();
        response.setId(userProfile.getId());
        response.setName(userProfile.getPersonalInfo().getName());
        response.setLastName(userProfile.getPersonalInfo().getLastName());
        response.setAge(userProfile.getPersonalInfo().getAge());
        response.setEmail(userProfile.getContactInfo().getEmail());
        response.setPhoneNumber(userProfile.getContactInfo().getPhoneNumber());
        response.setProfileImageUrl(userProfile.getProfileImageUrl());
        response.setCreatedAt(userProfile.getCreatedAt());
        response.setUpdatedAt(userProfile.getUpdatedAt());
        return response;
    }

    public UserProfile toDomain(CreateUserProfileCommand command) {
        PersonalInfo personalInfo = new PersonalInfo(
                command.getName(),
                command.getLastName(),
                command.getAge()
        );

        ContactInfo contactInfo = new ContactInfo(
                command.getEmail(),
                command.getPhoneNumber()
        );

        return new UserProfile(personalInfo, contactInfo, command.getProfileImageUrl());
    }
}
