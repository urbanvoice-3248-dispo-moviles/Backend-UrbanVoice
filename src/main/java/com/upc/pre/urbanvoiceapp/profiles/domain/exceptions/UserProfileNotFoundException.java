package com.upc.pre.urbanvoiceapp.profiles.domain.exceptions;

import com.upc.pre.urbanvoiceapp.shared.exceptions.DomainException;

/**
 * Excepción lanzada cuando no se encuentra un perfil de usuario.
 */
public class UserProfileNotFoundException extends DomainException {
    
    public UserProfileNotFoundException(String email) {
        super("User profile not found with email: " + email);
    }

    public UserProfileNotFoundException(Long id) {
        super("User profile not found with id: " + id);
    }
}
