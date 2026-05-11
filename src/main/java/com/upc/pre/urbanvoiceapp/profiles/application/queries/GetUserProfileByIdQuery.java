package com.upc.pre.urbanvoiceapp.profiles.application.queries;

/**
 * Query para obtener un perfil de usuario por ID.
 */
public class GetUserProfileByIdQuery {
    private final Long userId;

    public GetUserProfileByIdQuery(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
