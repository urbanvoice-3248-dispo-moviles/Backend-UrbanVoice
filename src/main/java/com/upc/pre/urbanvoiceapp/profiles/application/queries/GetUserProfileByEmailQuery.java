package com.upc.pre.urbanvoiceapp.profiles.application.queries;

/**
 * Query para obtener un perfil de usuario por email.
 */
public class GetUserProfileByEmailQuery {
    private final String email;

    public GetUserProfileByEmailQuery(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
