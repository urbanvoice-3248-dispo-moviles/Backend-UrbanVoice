package com.upc.pre.urbanvoiceapp.districts.application.commands;

public class DeleteDistrictCommand {
    private final Long districtId;

    public DeleteDistrictCommand(Long districtId) {
        this.districtId = districtId;
    }

    public Long getDistrictId() { return districtId; }
}
