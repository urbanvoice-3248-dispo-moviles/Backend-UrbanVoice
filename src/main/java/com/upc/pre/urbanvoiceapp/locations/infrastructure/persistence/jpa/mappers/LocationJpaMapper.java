package com.upc.pre.urbanvoiceapp.locations.infrastructure.persistence.jpa.mappers;

import com.upc.pre.urbanvoiceapp.locations.domain.entities.Location;
import com.upc.pre.urbanvoiceapp.locations.domain.valueobjects.GeoCoordinate;
import com.upc.pre.urbanvoiceapp.locations.infrastructure.persistence.jpa.entities.LocationJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class LocationJpaMapper {

    public Location toDomain(LocationJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;

        GeoCoordinate coordinate = new GeoCoordinate(
                jpaEntity.getLatitude(),
                jpaEntity.getLongitude()
        );

        Location location = new Location(
                jpaEntity.getId(),
                coordinate,
                jpaEntity.getAddress(),
                jpaEntity.getDistrict()
        );

        if (jpaEntity.getDescription() != null) {
            location.updateDescription(jpaEntity.getDescription());
        }

        return location;
    }

    public LocationJpaEntity toJpa(Location domain) {
        if (domain == null) return null;

        LocationJpaEntity jpaEntity = new LocationJpaEntity();
        jpaEntity.setId(domain.getId());
        jpaEntity.setLatitude(domain.getCoordinate().getLatitude());
        jpaEntity.setLongitude(domain.getCoordinate().getLongitude());
        jpaEntity.setAddress(domain.getAddress());
        jpaEntity.setDistrict(domain.getDistrict());
        jpaEntity.setDescription(domain.getDescription());
        jpaEntity.setCreatedAt(domain.getCreatedAt());

        return jpaEntity;
    }
}
