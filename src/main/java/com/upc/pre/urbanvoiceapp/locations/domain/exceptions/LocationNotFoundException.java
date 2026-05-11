package com.upc.pre.urbanvoiceapp.locations.domain.exceptions;

import com.upc.pre.urbanvoiceapp.shared.exceptions.DomainException;

/**
 * Excepción lanzada cuando no se encuentra una ubicación.
 */
public class LocationNotFoundException extends DomainException {
    
    public LocationNotFoundException(Long id) {
        super("Location not found with id: " + id);
    }

    public LocationNotFoundException(Double latitude, Double longitude) {
        super("Location not found near: " + latitude + ", " + longitude);
    }
}
