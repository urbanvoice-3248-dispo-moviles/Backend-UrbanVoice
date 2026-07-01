package com.upc.pre.urbanvoiceapp.districts.application.commands;

import com.upc.pre.urbanvoiceapp.districts.domain.valueobjects.GeoPoint;

import java.util.List;

public class UpdateDistrictCommand {
    private final Long districtId;
    private final String name;
    private final Integer riskLevel;
    private final String riskDescription;
    private final List<GeoPoint> boundary;
    private final String description;

    public UpdateDistrictCommand(Long districtId, String name, Integer riskLevel, String riskDescription, List<GeoPoint> boundary, String description) {
        this.districtId = districtId;
        this.name = name;
        this.riskLevel = riskLevel;
        this.riskDescription = riskDescription;
        this.boundary = boundary;
        this.description = description;
    }

    public Long getDistrictId() { return districtId; }
    public String getName() { return name; }
    public Integer getRiskLevel() { return riskLevel; }
    public String getRiskDescription() { return riskDescription; }
    public List<GeoPoint> getBoundary() { return boundary; }
    public String getDescription() { return description; }
}
