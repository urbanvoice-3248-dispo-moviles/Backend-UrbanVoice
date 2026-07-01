package com.upc.pre.urbanvoiceapp.locations.domain.exceptions;

import com.upc.pre.urbanvoiceapp.shared.exceptions.DomainException;

public class LocationNotFoundException extends DomainException {
    public LocationNotFoundException(Long id) {
        super("Location not found with id: " + id);
    }
}
