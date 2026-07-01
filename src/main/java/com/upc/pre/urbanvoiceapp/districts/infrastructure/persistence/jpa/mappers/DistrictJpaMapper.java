package com.upc.pre.urbanvoiceapp.districts.infrastructure.persistence.jpa.mappers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upc.pre.urbanvoiceapp.districts.domain.entities.District;
import com.upc.pre.urbanvoiceapp.districts.domain.valueobjects.GeoPoint;
import com.upc.pre.urbanvoiceapp.districts.infrastructure.persistence.jpa.entities.DistrictJpaEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class DistrictJpaMapper {

    private final ObjectMapper objectMapper;

    public DistrictJpaMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public District toDomain(DistrictJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;

        List<GeoPoint> boundary = parseBoundary(jpaEntity.getBoundary());

        return new District(
                jpaEntity.getId(),
                jpaEntity.getName(),
                jpaEntity.getRiskLevel() != null ? jpaEntity.getRiskLevel() : 0,
                jpaEntity.getRiskDescription(),
                boundary,
                jpaEntity.getIncidentCount() != null ? jpaEntity.getIncidentCount() : 0,
                jpaEntity.getDescription()
        );
    }

    public DistrictJpaEntity toJpa(District domain) {
        if (domain == null) return null;

        DistrictJpaEntity jpaEntity = new DistrictJpaEntity();
        jpaEntity.setId(domain.getId());
        jpaEntity.setName(domain.getName());
        jpaEntity.setRiskLevel(domain.getRiskLevel());
        jpaEntity.setRiskDescription(domain.getRiskDescription());
        jpaEntity.setBoundary(serializeBoundary(domain.getBoundary()));
        jpaEntity.setDescription(domain.getDescription());
        jpaEntity.setIncidentCount(domain.getIncidentCount());
        jpaEntity.setCreatedAt(domain.getCreatedAt());
        jpaEntity.setUpdatedAt(domain.getUpdatedAt());

        return jpaEntity;
    }

    private String serializeBoundary(List<GeoPoint> points) {
        if (points == null || points.isEmpty()) return null;
        try {
            List<PointDto> dtos = points.stream()
                    .map(p -> new PointDto(p.getLatitude(), p.getLongitude()))
                    .toList();
            return objectMapper.writeValueAsString(dtos);
        } catch (Exception e) {
            return null;
        }
    }

    private List<GeoPoint> parseBoundary(String json) {
        if (json == null || json.isBlank()) return Collections.emptyList();
        try {
            List<PointDto> dtos = objectMapper.readValue(json, new TypeReference<List<PointDto>>() {});
            return dtos.stream()
                    .map(d -> new GeoPoint(d.latitude(), d.longitude()))
                    .toList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private record PointDto(double latitude, double longitude) {}
}
