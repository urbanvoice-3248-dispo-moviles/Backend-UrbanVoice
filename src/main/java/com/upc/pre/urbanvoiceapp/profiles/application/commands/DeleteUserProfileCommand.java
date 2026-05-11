package com.upc.pre.urbanvoiceapp.profiles.application.commands;

/**
 * Command para eliminar un perfil de usuario.
 */
public class DeleteUserProfileCommand {
    private final Long userId;

    public DeleteUserProfileCommand(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
