package com.upc.pre.urbanvoiceapp.profiles.application.commands;

/**
 * Command para crear un nuevo perfil de usuario.
 */
public class CreateUserProfileCommand {
    private final String name;
    private final String lastName;
    private final int age;
    private final String email;
    private final String phoneNumber;
    private final String profileImageUrl;
    private final String password;

    public CreateUserProfileCommand(String name, String lastName, int age, String email, String phoneNumber, String profileImageUrl, String password) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profileImageUrl = profileImageUrl;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getPassword() {
        return password;
    }
}
