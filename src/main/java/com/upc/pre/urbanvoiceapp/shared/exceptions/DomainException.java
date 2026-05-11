package com.upc.pre.urbanvoiceapp.shared.exceptions;

/**
 * Excepción base para todos los errores de dominio en UrbanVoice.
 */
public class DomainException extends RuntimeException {
    
    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
