package com.upc.pre.urbanvoiceapp.districts.domain.exceptions;

public class DistrictNotFoundException extends RuntimeException {
    public DistrictNotFoundException(Long id) {
        super("District not found with id: " + id);
    }

    public DistrictNotFoundException(String name) {
        super("District not found with name: " + name);
    }
}
