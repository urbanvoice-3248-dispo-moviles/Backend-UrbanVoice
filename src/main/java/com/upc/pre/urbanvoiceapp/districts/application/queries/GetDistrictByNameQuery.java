package com.upc.pre.urbanvoiceapp.districts.application.queries;

public class GetDistrictByNameQuery {
    private final String name;

    public GetDistrictByNameQuery(String name) {
        this.name = name;
    }

    public String getName() { return name; }
}
