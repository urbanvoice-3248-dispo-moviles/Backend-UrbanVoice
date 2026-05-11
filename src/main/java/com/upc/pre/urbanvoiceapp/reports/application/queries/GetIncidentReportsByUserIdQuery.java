package com.upc.pre.urbanvoiceapp.reports.application.queries;

/**
 * Query para obtener reportes de un usuario específico.
 */
public class GetIncidentReportsByUserIdQuery {
    private final Long userId;

    public GetIncidentReportsByUserIdQuery(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() { return userId; }
}
