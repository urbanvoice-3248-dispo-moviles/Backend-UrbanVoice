package com.upc.pre.urbanvoiceapp.reports.domain.exceptions;

import com.upc.pre.urbanvoiceapp.shared.exceptions.DomainException;

/**
 * Excepción lanzada cuando el tipo de incidente no es válido.
 */
public class InvalidIncidentTypeException extends DomainException {
    
    public InvalidIncidentTypeException(String type) {
        super("Invalid incident type: " + type + 
              ". Valid types are: ACCIDENT, ROBBERY, FIRE, FLOOD, OTHER");
    }
}
