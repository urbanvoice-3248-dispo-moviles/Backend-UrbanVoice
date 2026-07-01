package com.upc.pre.urbanvoiceapp.districts.interfaces.rest.resources.routeassessment;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RouteAssessmentRequest {
    @JsonProperty("waypoints")
    private List<WaypointResource> waypoints;

    public RouteAssessmentRequest() {}

    public RouteAssessmentRequest(List<WaypointResource> waypoints) {
        this.waypoints = waypoints;
    }

    public List<WaypointResource> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<WaypointResource> waypoints) {
        this.waypoints = waypoints;
    }

    public static class WaypointResource {
        @JsonProperty("lat")
        private Double lat;

        @JsonProperty("lng")
        private Double lng;

        public WaypointResource() {}

        public WaypointResource(Double lat, Double lng) {
            this.lat = lat;
            this.lng = lng;
        }

        public Double getLat() { return lat; }
        public void setLat(Double lat) { this.lat = lat; }
        public Double getLng() { return lng; }
        public void setLng(Double lng) { this.lng = lng; }
    }
}
