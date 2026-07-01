package com.upc.pre.urbanvoiceapp.locations.application.services;

import com.upc.pre.urbanvoiceapp.locations.domain.entities.Location;
import com.upc.pre.urbanvoiceapp.locations.domain.repositories.LocationRepository;
import com.upc.pre.urbanvoiceapp.locations.domain.valueobjects.GeoCoordinate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LocationApplicationService {

    private final LocationRepository locationRepository;

    public LocationApplicationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location createLocation(Double latitude, Double longitude, String address, String district) {
        GeoCoordinate coordinate = new GeoCoordinate(latitude, longitude);

        Location location = new Location(coordinate, address, district);
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
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public void deleteLocation(Long locationId) {
        locationRepository.deleteById(locationId);
    }
}
