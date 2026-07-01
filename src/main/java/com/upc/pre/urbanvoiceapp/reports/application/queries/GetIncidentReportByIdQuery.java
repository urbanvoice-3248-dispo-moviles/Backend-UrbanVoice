package com.upc.pre.urbanvoiceapp.reports.application.queries;

/**
 * Query para obtener un reporte por ID.
 *
 * <p>Encapsula el identificador recibido desde la capa de entrada para mantener
 * la firma del servicio de aplicacion orientada a mensajes.</p>
 */
public class GetIncidentReportByIdQuery {
    private final Long reportId;

    /**
     * Crea la query de busqueda por identificador.
     *
     * @param reportId identificador del reporte solicitado.
     */
    public GetIncidentReportByIdQuery(Long reportId) {
        this.reportId = reportId;
    }

    /** @return identificador del reporte solicitado. */
    public Long getReportId() { return reportId; }
}
