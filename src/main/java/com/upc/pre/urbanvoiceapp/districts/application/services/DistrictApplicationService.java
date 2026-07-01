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
}
