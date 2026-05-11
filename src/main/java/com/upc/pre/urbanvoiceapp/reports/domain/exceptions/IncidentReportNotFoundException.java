package com.upc.pre.urbanvoiceapp.reports.domain.exceptions;

import com.upc.pre.urbanvoiceapp.shared.exceptions.DomainException;

/**
 * Excepción lanzada cuando no se encuentra un reporte de incidente.
 */
public class IncidentReportNotFoundException extends DomainException {
    
    public IncidentReportNotFoundException(Long id) {
        super("Incident report not found with id: " + id);
    }
}
