package com.upc.pre.urbanvoiceapp.districts.application.services;

import com.upc.pre.urbanvoiceapp.districts.application.commands.CreateDistrictCommand;
import com.upc.pre.urbanvoiceapp.districts.application.commands.DeleteDistrictCommand;
import com.upc.pre.urbanvoiceapp.districts.application.commands.UpdateDistrictCommand;
import com.upc.pre.urbanvoiceapp.districts.application.queries.GetDistrictByIdQuery;
import com.upc.pre.urbanvoiceapp.districts.application.queries.GetDistrictByNameQuery;
import com.upc.pre.urbanvoiceapp.districts.domain.entities.District;
import com.upc.pre.urbanvoiceapp.districts.domain.exceptions.DistrictNotFoundException;
import com.upc.pre.urbanvoiceapp.districts.domain.repositories.DistrictRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upc.pre.urbanvoiceapp.districts.domain.valueobjects.GeoPoint;
import java.util.List;

@Service
@Transactional
public class DistrictApplicationService {

    private final DistrictRepository districtRepository;

    public DistrictApplicationService(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    public District handle(CreateDistrictCommand command) {
        if (districtRepository.existsByName(command.getName())) {
            throw new IllegalArgumentException("District already exists with name: " + command.getName());
        }

        District district = new District(
                command.getName(),
                command.getRiskLevel(),
                command.getRiskDescription() != null ? command.getRiskDescription() : "Nivel de riesgo inicial",
                command.getBoundary()
        );

        if (command.getDescription() != null) {
            district.updateDescription(command.getDescription());
        }

        district.validate();
        return districtRepository.save(district);
    }

    public District handle(UpdateDistrictCommand command) {
        District district = districtRepository.findById(command.getDistrictId())
                .orElseThrow(() -> new DistrictNotFoundException(command.getDistrictId()));

        if (command.getName() != null) {
            district = new District(
                    district.getId(),
                    command.getName(),
                    district.getRiskLevel(),
                    district.getRiskDescription(),
                    district.getBoundary()
            );
        }

        if (command.getRiskLevel() != null) {
            district.updateRiskLevel(
                    command.getRiskLevel(),
                    command.getRiskDescription() != null ? command.getRiskDescription() : district.getRiskDescription()
            );
        }

        if (command.getBoundary() != null) {
            district = new District(
                    district.getId(),
                    district.getName(),
                    district.getRiskLevel(),
                    district.getRiskDescription(),
                    command.getBoundary()
            );
        }

        if (command.getDescription() != null) {
            district.updateDescription(command.getDescription());
        }

        district.validate();
        return districtRepository.save(district);
    }

    public void handle(DeleteDistrictCommand command) {
        if (!districtRepository.findById(command.getDistrictId()).isPresent()) {
            throw new DistrictNotFoundException(command.getDistrictId());
        }
        districtRepository.deleteById(command.getDistrictId());
    }

    @Transactional(readOnly = true)
    public District handle(GetDistrictByIdQuery query) {
        return districtRepository.findById(query.getDistrictId())
                .orElseThrow(() -> new DistrictNotFoundException(query.getDistrictId()));
    }

    @Transactional(readOnly = true)
    public District handle(GetDistrictByNameQuery query) {
        return districtRepository.findByName(query.getName())
                .orElseThrow(() -> new DistrictNotFoundException(query.getName()));
    }

    @Transactional(readOnly = true)
    public List<District> getAllDistricts() {
        return districtRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<District> getDangerousDistricts(int minRiskLevel) {
        return districtRepository.findByRiskLevelGreaterThan(minRiskLevel);
    }

    public void recordIncidentAtLocation(double lat, double lng) {
        List<District> allDistricts = districtRepository.findAll();
        District matched = findDistrictForPoint(lat, lng, allDistricts);
        if (matched == null) return;

        matched.incrementIncidentCount();
        int newRisk = calculateRiskFromIncidentCount(matched.getIncidentCount());
        String category = switch (newRisk) {
            case 0, 1 -> "SEGURO";
            case 2, 3 -> "MODERADO";
            default -> "PELIGROSO";
        };
        if (newRisk != matched.getRiskLevel()) {
            matched.updateRiskLevel(newRisk, "Riesgo " + category.toLowerCase() + " - " + matched.getIncidentCount() + " incidente(s)");
        }
        districtRepository.save(matched);
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

    private int calculateRiskFromIncidentCount(int count) {
        if (count <= 0) return 0;
        if (count <= 2) return 1;
        if (count <= 5) return 2;
        if (count <= 10) return 3;
        if (count <= 20) return 4;
        return 5;
    }
}
