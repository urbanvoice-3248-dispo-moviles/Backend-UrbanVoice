package com.upc.pre.urbanvoiceapp.reports.application.commands;

/**
 * Command para eliminar un reporte de incidente.
 */
public class DeleteIncidentReportCommand {
    private final Long reportId;

    public DeleteIncidentReportCommand(Long reportId) {
        this.reportId = reportId;
    }

    public Long getReportId() { return reportId; }
}
