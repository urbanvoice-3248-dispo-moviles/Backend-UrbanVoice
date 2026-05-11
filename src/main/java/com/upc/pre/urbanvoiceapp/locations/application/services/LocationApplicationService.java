package com.upc.pre.urbanvoiceapp.locations.application.services;

import com.upc.pre.urbanvoiceapp.locations.domain.entities.Location;
import com.upc.pre.urbanvoiceapp.locations.domain.repositories.LocationRepository;
import com.upc.pre.urbanvoiceapp.locations.domain.valueobjects.GeoCoordinate;
import com.upc.pre.urbanvoiceapp.locations.domain.valueobjects.RiskLevel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Application Service para orquestar operaciones sobre ubicaciones.
 */
@Service
@Transactional
public class LocationApplicationService {

    private final LocationRepository locationRepository;

    public LocationApplicationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location createLocation(Double latitude, Double longitude, String address, String district, Integer riskLevel) {
        GeoCoordinate coordinate = new GeoCoordinate(latitude, longitude);
        RiskLevel risk = new RiskLevel(riskLevel, "Nivel de riesgo inicial");
        
        Location location = new Location(coordinate, address, district, risk);
        location.validate();
        
        return locationRepository.save(location);
    }

    @Transactional(readOnly = true)
    public Optional<Location> getLocationById(Long id) {
        return locationRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Location> getNearbyLocations(Double latitude, Double longitude, Double radiusInKm) {
        return locationRepository.findNearby(latitude, longitude, radiusInKm);
    }

    @Transactional(readOnly = true)
    public List<Location> getLocationsByDistrict(String district) {
        return locationRepository.findByDistrict(district);
    }

    @Transactional(readOnly = true)
    public List<Location> getDangerousLocations(Integer minRiskLevel) {
        return locationRepository.findByRiskLevelGreaterThan(minRiskLevel);
    }

    public void updateLocationIncidentCount(Long locationId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Ubicación no encontrada"));
        location.incrementIncidentCount();
        locationRepository.save(location);
    }

    @Transactional(readOnly = true)
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public void deleteLocation(Long locationId) {
        locationRepository.deleteById(locationId);
    }
}
