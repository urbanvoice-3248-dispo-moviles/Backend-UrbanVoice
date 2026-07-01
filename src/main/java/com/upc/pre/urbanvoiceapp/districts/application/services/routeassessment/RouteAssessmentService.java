package com.upc.pre.urbanvoiceapp.districts.application.services.routeassessment;

import com.upc.pre.urbanvoiceapp.districts.domain.entities.District;
import com.upc.pre.urbanvoiceapp.districts.domain.repositories.DistrictRepository;
import com.upc.pre.urbanvoiceapp.districts.domain.valueobjects.GeoPoint;
import com.upc.pre.urbanvoiceapp.districts.interfaces.rest.resources.routeassessment.RouteAssessmentRequest;
import com.upc.pre.urbanvoiceapp.districts.interfaces.rest.resources.routeassessment.RouteAssessmentResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class RouteAssessmentService {

    private final DistrictRepository districtRepository;

    public RouteAssessmentService(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    public RouteAssessmentResponse assessRoute(RouteAssessmentRequest request) {
        List<District> allDistricts = districtRepository.findAll();
        List<RouteAssessmentResponse.SegmentAssessment> segments = new ArrayList<>();

        int index = 0;
        for (RouteAssessmentRequest.WaypointResource wp : request.getWaypoints()) {
            District matchedDistrict = findDistrictForPoint(wp.getLat(), wp.getLng(), allDistricts);
            if (matchedDistrict != null) {
                segments.add(new RouteAssessmentResponse.SegmentAssessment(
                        index,
                        matchedDistrict.getName(),
                        matchedDistrict.getRiskLevel(),
                        matchedDistrict.getRiskCategory()
                ));
            } else {
                segments.add(new RouteAssessmentResponse.SegmentAssessment(
                        index,
                        "Desconocido",
                        0,
                        "SEGURO"
                ));
            }
            index++;
        }

        double overallScore = segments.stream()
                .mapToInt(RouteAssessmentResponse.SegmentAssessment::getRiskLevel)
                .average()
                .orElse(0.0);

        return new RouteAssessmentResponse(segments, overallScore);
    }

    private District findDistrictForPoint(double lat, double lng, List<District> districts) {
        for (District district : districts) {
            if (isPointInPolygon(lat, lng, district.getBoundary())) {
                return district;
            }
        }
        return null;
    }

    private boolean isPointInPolygon(double px, double py, List<GeoPoint> polygon) {
        if (polygon == null || polygon.size() < 3) return false;

        boolean inside = false;
        int n = polygon.size();
        for (int i = 0, j = n - 1; i < n; j = i++) {
            double xi = polygon.get(i).getLatitude();
            double yi = polygon.get(i).getLongitude();
            double xj = polygon.get(j).getLatitude();
            double yj = polygon.get(j).getLongitude();

            if ((yi > py) != (yj > py) &&
                    px < (xj - xi) * (py - yi) / (yj - yi) + xi) {
                inside = !inside;
            }
        }
        return inside;
    }
}
