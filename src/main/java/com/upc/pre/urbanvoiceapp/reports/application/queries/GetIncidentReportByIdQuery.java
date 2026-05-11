package com.upc.pre.urbanvoiceapp.reports.application.queries;

/**
 * Query para obtener un reporte por ID.
 */
public class GetIncidentReportByIdQuery {
    private final Long reportId;

    public GetIncidentReportByIdQuery(Long reportId) {
        this.reportId = reportId;
    }

    public Long getReportId() { return reportId; }
}
