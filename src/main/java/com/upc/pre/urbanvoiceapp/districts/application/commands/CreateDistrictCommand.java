package com.upc.pre.urbanvoiceapp.districts.application.commands;

import com.upc.pre.urbanvoiceapp.districts.domain.valueobjects.GeoPoint;

import java.util.List;

public class CreateDistrictCommand {
    private final String name;
    private final int riskLevel;
    private final String riskDescription;
    private final List<GeoPoint> boundary;
    private final String description;

    public CreateDistrictCommand(String name, int riskLevel, String riskDescription, List<GeoPoint> boundary, String description) {
        this.name = name;
        this.riskLevel = riskLevel;
        this.riskDescription = riskDescription;
        this.boundary = boundary;
        this.description = description;
    }

    public String getName() { return name; }
    public int getRiskLevel() { return riskLevel; }
    public String getRiskDescription() { return riskDescription; }
    public List<GeoPoint> getBoundary() { return boundary; }
    public String getDescription() { return description; }
}
