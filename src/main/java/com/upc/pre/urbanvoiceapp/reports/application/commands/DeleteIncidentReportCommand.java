package com.upc.pre.urbanvoiceapp.reports.application.commands;

/**
 * Command para eliminar un reporte de incidente.
 *
 * <p>Representa la intencion de remover un reporte existente identificado por
 * su llave de dominio.</p>
 */
public class DeleteIncidentReportCommand {
    private final Long reportId;

    /**
     * Crea el comando de eliminacion.
     *
     * @param reportId identificador del reporte que se eliminara.
     */
    public DeleteIncidentReportCommand(Long reportId) {
        this.reportId = reportId;
    }

    /** @return identificador del reporte objetivo. */
    public Long getReportId() { return reportId; }
}
