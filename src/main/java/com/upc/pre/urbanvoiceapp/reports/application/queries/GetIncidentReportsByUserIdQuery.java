package com.upc.pre.urbanvoiceapp.reports.application.queries;

/**
 * Query para obtener reportes de un usuario especifico.
 *
 * <p>Representa la consulta de todos los reportes asociados a un ciudadano.</p>
 */
public class GetIncidentReportsByUserIdQuery {
    private final Long userId;

    /**
     * Crea la query de reportes por usuario.
     *
     * @param userId identificador del usuario propietario de los reportes.
     */
    public GetIncidentReportsByUserIdQuery(Long userId) {
        this.userId = userId;
    }

    /** @return identificador del usuario consultado. */
    public Long getUserId() { return userId; }
}
