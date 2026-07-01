package com.upc.pre.urbanvoiceapp.reports.domain.exceptions;

import com.upc.pre.urbanvoiceapp.shared.exceptions.DomainException;

/**
 * Excepcion lanzada cuando el tipo de incidente no es valido.
 */
public class InvalidIncidentTypeException extends DomainException {

    /**
     * Crea la excepcion con el tipo rechazado.
     *
     * @param type tipo de incidente recibido.
     */
    public InvalidIncidentTypeException(String type) {
        super("Invalid incident type: " + type +
              ". Valid types are: ACCIDENT, ROBBERY, FIRE, FLOOD, OTHER");
    }
}
