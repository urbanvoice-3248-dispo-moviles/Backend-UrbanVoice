package com.upc.pre.urbanvoiceapp.profiles.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para crear un nuevo perfil de usuario.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserProfileResource {
    @JsonProperty("name")
    private String name;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("age")
    private Integer age;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("profile_image_url")
    private String profileImageUrl;

    @JsonProperty("password")
    private String password;
}
