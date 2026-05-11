package com.upc.pre.urbanvoiceapp.profiles.domain.exceptions;

import com.upc.pre.urbanvoiceapp.shared.exceptions.DomainException;

/**
 * Excepción lanzada cuando ya existe un perfil de usuario con el mismo email.
 */
public class DuplicateUserProfileException extends DomainException {
    
    public DuplicateUserProfileException(String email) {
        super("User profile already exists with email: " + email);
    }
}
