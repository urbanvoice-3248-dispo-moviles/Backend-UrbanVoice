package com.upc.pre.urbanvoiceapp.profiles.application.commands;

/**
 * Command para actualizar un perfil de usuario existente.
 */
public class UpdateUserProfileCommand {
    private final Long userId;
    private final String name;
    private final String lastName;
    private final int age;
    private final String phoneNumber;
    private final String profileImageUrl;

    public UpdateUserProfileCommand(Long userId, String name, String lastName, int age, String phoneNumber, String profileImageUrl) {
        this.userId = userId;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.profileImageUrl = profileImageUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
