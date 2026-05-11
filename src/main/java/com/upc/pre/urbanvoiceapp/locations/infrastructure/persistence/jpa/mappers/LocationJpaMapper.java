package com.upc.pre.urbanvoiceapp.locations.infrastructure.persistence.jpa.mappers;

import com.upc.pre.urbanvoiceapp.locations.domain.entities.Location;
import com.upc.pre.urbanvoiceapp.locations.domain.valueobjects.GeoCoordinate;
import com.upc.pre.urbanvoiceapp.locations.domain.valueobjects.RiskLevel;
import com.upc.pre.urbanvoiceapp.locations.infrastructure.persistence.jpa.entities.LocationJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre el modelo de Dominio (Location) y la entidad JPA.
 */
@Component
public class LocationJpaMapper {

    public Location toDomain(LocationJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }

        GeoCoordinate coordinate = new GeoCoordinate(
                jpaEntity.getLatitude(),
                jpaEntity.getLongitude()
        );

        RiskLevel riskLevel = new RiskLevel(
                jpaEntity.getRiskLevel(),
                jpaEntity.getRiskDescription() != null ? jpaEntity.getRiskDescription() : ""
        );

        Location location = new Location(
                jpaEntity.getId(),
                coordinate,
                jpaEntity.getAddress(),
                jpaEntity.getDistrict(),
                riskLevel
        );

        location.setIncidentCount(jpaEntity.getIncidentCount());
        location.setDescription(jpaEntity.getDescription());
        location.setLastUpdated(jpaEntity.getLastUpdated());

        return location;
    }

    public LocationJpaEntity toJpa(Location domain) {
        if (domain == null) {
            return null;
        }

        LocationJpaEntity jpaEntity = new LocationJpaEntity();
        jpaEntity.setId(domain.getId());
        jpaEntity.setLatitude(domain.getCoordinate().getLatitude());
        jpaEntity.setLongitude(domain.getCoordinate().getLongitude());
        jpaEntity.setAddress(domain.getAddress());
        jpaEntity.setDistrict(domain.getDistrict());
        jpaEntity.setRiskLevel(domain.getRiskLevel().getLevel());
        jpaEntity.setRiskDescription(domain.getRiskLevel().getDescription());
        jpaEntity.setIncidentCount(domain.getIncidentCount());
        jpaEntity.setDescription(domain.getDescription());
        jpaEntity.setLastUpdated(domain.getLastUpdated());

        return jpaEntity;
    }
}
