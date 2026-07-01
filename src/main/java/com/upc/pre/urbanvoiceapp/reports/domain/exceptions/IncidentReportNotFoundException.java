package com.upc.pre.urbanvoiceapp.reports.domain.exceptions;

import com.upc.pre.urbanvoiceapp.shared.exceptions.DomainException;

/**
 * Excepcion lanzada cuando no se encuentra un reporte de incidente.
 */
public class IncidentReportNotFoundException extends DomainException {

    /**
     * Crea la excepcion con el identificador no encontrado.
     *
     * @param id identificador del reporte solicitado.
     */
    public IncidentReportNotFoundException(Long id) {
        super("Incident report not found with id: " + id);
    }
}
