package com.upc.pre.urbanvoiceapp.locations.domain.repositories;

import com.upc.pre.urbanvoiceapp.locations.domain.entities.Location;

import java.util.List;
import java.util.Optional;

public interface LocationRepository {

    Location save(Location location);

    Optional<Location> findById(Long id);

    List<Location> findNearby(double latitude, double longitude, double radiusInKm);

    List<Location> findByDistrict(String district);

    void deleteById(Long id);

    List<Location> findAll();
}
