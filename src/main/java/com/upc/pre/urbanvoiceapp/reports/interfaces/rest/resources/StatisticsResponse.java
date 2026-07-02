package com.upc.pre.urbanvoiceapp.reports.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsResponse {
    @JsonProperty("total_reports")
    private long totalReports;

    @JsonProperty("reports_by_type")
    private Map<String, Long> reportsByType;

    @JsonProperty("reports_by_status")
    private Map<String, Long> reportsByStatus;
}
