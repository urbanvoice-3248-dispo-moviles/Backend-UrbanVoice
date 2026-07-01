package com.upc.pre.urbanvoiceapp.districts.application.queries;

public class GetDistrictByIdQuery {
    private final Long districtId;

    public GetDistrictByIdQuery(Long districtId) {
        this.districtId = districtId;
    }

    public Long getDistrictId() { return districtId; }
}
