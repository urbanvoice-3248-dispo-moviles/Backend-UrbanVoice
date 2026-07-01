package com.upc.pre.urbanvoiceapp.reports.application.commands;

/**
 * Command para actualizar un reporte de incidente existente.
 *
 * <p>Contiene solo los campos editables desde la capa REST. Los valores nulos
 * se interpretan como campos sin cambios.</p>
 */
public class UpdateIncidentReportCommand {
    private final Long reportId;
    private final String title;
    private final String description;
    private final String mediaUrl;

    /**
     * Crea el comando de actualizacion parcial de un reporte.
     *
     * @param reportId identificador del reporte a actualizar.
     * @param title nuevo titulo, o {@code null} si no cambia.
     * @param description nueva descripcion, o {@code null} si no cambia.
     * @param mediaUrl nueva URL multimedia, o {@code null} si no cambia.
     */
    public UpdateIncidentReportCommand(Long reportId, String title, String description, String mediaUrl) {
        this.reportId = reportId;
        this.title = title;
        this.description = description;
        this.mediaUrl = mediaUrl;
    }

    /** @return identificador del reporte a modificar. */
    public Long getReportId() { return reportId; }

    /** @return nuevo titulo solicitado, o {@code null}. */
    public String getTitle() { return title; }

    /** @return nueva descripcion solicitada, o {@code null}. */
    public String getDescription() { return description; }

    /** @return nueva URL multimedia solicitada, o {@code null}. */
    public String getMediaUrl() { return mediaUrl; }
}
