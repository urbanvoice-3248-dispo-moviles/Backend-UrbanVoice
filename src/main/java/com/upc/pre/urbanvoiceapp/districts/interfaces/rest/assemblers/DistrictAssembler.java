package com.upc.pre.urbanvoiceapp.districts.interfaces.rest.assemblers;

import com.upc.pre.urbanvoiceapp.districts.application.commands.CreateDistrictCommand;
import com.upc.pre.urbanvoiceapp.districts.application.commands.UpdateDistrictCommand;
import com.upc.pre.urbanvoiceapp.districts.domain.entities.District;
import com.upc.pre.urbanvoiceapp.districts.domain.valueobjects.GeoPoint;
import com.upc.pre.urbanvoiceapp.districts.interfaces.rest.resources.CreateDistrictResource;
import com.upc.pre.urbanvoiceapp.districts.interfaces.rest.resources.DistrictResponse;
import com.upc.pre.urbanvoiceapp.districts.interfaces.rest.resources.UpdateDistrictResource;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DistrictAssembler {

    public CreateDistrictCommand toCreateCommand(CreateDistrictResource resource) {
        List<GeoPoint> boundary = resource.getBoundary() != null
                ? resource.getBoundary().stream()
                    .map(bp -> new GeoPoint(bp.getLatitude(), bp.getLongitude()))
                    .collect(Collectors.toList())
                : Collections.emptyList();

        return new CreateDistrictCommand(
                resource.getName(),
                resource.getRiskLevel(),
                resource.getRiskDescription(),
                boundary,
                resource.getDescription()
        );
    }

    public UpdateDistrictCommand toUpdateCommand(Long districtId, UpdateDistrictResource resource) {
        List<GeoPoint> boundary = resource.getBoundary() != null
                ? resource.getBoundary().stream()
                    .map(bp -> new GeoPoint(bp.getLatitude(), bp.getLongitude()))
                    .collect(Collectors.toList())
                : null;

        return new UpdateDistrictCommand(
                districtId,
                resource.getName(),
                resource.getRiskLevel(),
                resource.getRiskDescription(),
                boundary,
                resource.getDescription()
        );
    }

    public DistrictResponse toResponse(District district) {
        DistrictResponse response = new DistrictResponse();
        response.setId(district.getId());
        response.setName(district.getName());
        response.setRiskLevel(district.getRiskLevel());
        response.setRiskCategory(district.getRiskCategory());
        response.setRiskDescription(district.getRiskDescription());

        if (district.getBoundary() != null) {
            response.setBoundary(
                    district.getBoundary().stream()
                            .map(gp -> {
                                DistrictResponse.BoundaryPointResponse bp = new DistrictResponse.BoundaryPointResponse();
                                bp.setLatitude(gp.getLatitude());
                                bp.setLongitude(gp.getLongitude());
                                return bp;
                            })
                            .collect(Collectors.toList())
            );
        }

        response.setDescription(district.getDescription());
        response.setIncidentCount(district.getIncidentCount());
        response.setCreatedAt(district.getCreatedAt());
        response.setUpdatedAt(district.getUpdatedAt());
        return response;
    }
}
