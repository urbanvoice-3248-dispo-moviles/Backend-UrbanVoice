package com.upc.pre.urbanvoiceapp.reports.application.commands;

/**
 * Command para actualizar un reporte de incidente existente.
 */
public class UpdateIncidentReportCommand {
    private final Long reportId;
    private final String title;
    private final String description;
    private final String mediaUrl;

    public UpdateIncidentReportCommand(Long reportId, String title, String description, String mediaUrl) {
        this.reportId = reportId;
        this.title = title;
        this.description = description;
        this.mediaUrl = mediaUrl;
    }

    public Long getReportId() { return reportId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getMediaUrl() { return mediaUrl; }
}
