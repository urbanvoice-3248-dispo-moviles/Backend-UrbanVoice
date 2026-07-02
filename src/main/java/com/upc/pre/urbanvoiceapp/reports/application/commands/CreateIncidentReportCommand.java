package com.upc.pre.urbanvoiceapp.reports.application.commands;

/**
 * Command para crear un nuevo reporte de incidente.
 */
public class CreateIncidentReportCommand {
    private final Long userId;
    private final String incidentType;
    private final String title;
    private final String description;
    private final Double latitude;
    private final Double longitude;
    private final String address;
    private final String mediaUrl;
    private final Boolean isAnonymous;
    private final String reportedAt;

    public CreateIncidentReportCommand(Long userId, String incidentType, String title, 
                                      String description, Double latitude, Double longitude,
                                      String address, String mediaUrl, Boolean isAnonymous) {
        this(userId, incidentType, title, description, latitude, longitude, address, mediaUrl, isAnonymous, null);
    }

    public CreateIncidentReportCommand(Long userId, String incidentType, String title, 
                                      String description, Double latitude, Double longitude,
                                      String address, String mediaUrl, Boolean isAnonymous,
                                      String reportedAt) {
        this.userId = userId;
        this.incidentType = incidentType;
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.mediaUrl = mediaUrl;
        this.isAnonymous = isAnonymous != null ? isAnonymous : false;
        this.reportedAt = reportedAt;
    }

    public Long getUserId() { return userId; }
    public String getIncidentType() { return incidentType; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public String getAddress() { return address; }
    public String getMediaUrl() { return mediaUrl; }
    public Boolean getIsAnonymous() { return isAnonymous; }
    public String getReportedAt() { return reportedAt; }
}
