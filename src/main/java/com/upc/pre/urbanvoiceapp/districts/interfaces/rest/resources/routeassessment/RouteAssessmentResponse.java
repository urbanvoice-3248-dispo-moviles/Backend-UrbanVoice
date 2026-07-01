package com.upc.pre.urbanvoiceapp.districts.interfaces.rest.resources.routeassessment;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RouteAssessmentResponse {
    @JsonProperty("segments")
    private List<SegmentAssessment> segments;

    @JsonProperty("overall_safety_score")
    private Double overallSafetyScore;

    public RouteAssessmentResponse() {}

    public RouteAssessmentResponse(List<SegmentAssessment> segments, Double overallSafetyScore) {
        this.segments = segments;
        this.overallSafetyScore = overallSafetyScore;
    }

    public List<SegmentAssessment> getSegments() { return segments; }
    public void setSegments(List<SegmentAssessment> segments) { this.segments = segments; }
    public Double getOverallSafetyScore() { return overallSafetyScore; }
    public void setOverallSafetyScore(Double overallSafetyScore) { this.overallSafetyScore = overallSafetyScore; }

    public static class SegmentAssessment {
        @JsonProperty("index")
        private int index;

        @JsonProperty("district")
        private String district;

        @JsonProperty("risk_level")
        private int riskLevel;

        @JsonProperty("risk_category")
        private String riskCategory;

        public SegmentAssessment() {}

        public SegmentAssessment(int index, String district, int riskLevel, String riskCategory) {
            this.index = index;
            this.district = district;
            this.riskLevel = riskLevel;
            this.riskCategory = riskCategory;
        }

        public int getIndex() { return index; }
        public void setIndex(int index) { this.index = index; }
        public String getDistrict() { return district; }
        public void setDistrict(String district) { this.district = district; }
        public int getRiskLevel() { return riskLevel; }
        public void setRiskLevel(int riskLevel) { this.riskLevel = riskLevel; }
        public String getRiskCategory() { return riskCategory; }
        public void setRiskCategory(String riskCategory) { this.riskCategory = riskCategory; }
    }
}
