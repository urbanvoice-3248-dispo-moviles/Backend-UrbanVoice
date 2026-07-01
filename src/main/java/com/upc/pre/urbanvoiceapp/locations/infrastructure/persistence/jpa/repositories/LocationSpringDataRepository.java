package com.upc.pre.urbanvoiceapp.locations.infrastructure.persistence.jpa.repositories;

import com.upc.pre.urbanvoiceapp.locations.infrastructure.persistence.jpa.entities.LocationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationSpringDataRepository extends JpaRepository<LocationJpaEntity, Long> {
    List<LocationJpaEntity> findByDistrict(String district);

    @Query(value = "SELECT * FROM locations WHERE " +
            "( 6371 * acos( cos( radians(:latitude) ) * cos( radians( latitude ) ) * " +
            "cos( radians( longitude ) - radians(:longitude) ) + sin( radians(:latitude) ) * " +
            "sin( radians( latitude ) ) ) ) <= :radiusInKm",
            nativeQuery = true)
    List<LocationJpaEntity> findNearby(@Param("latitude") Double latitude,
                                        @Param("longitude") Double longitude,
                                        @Param("radiusInKm") Double radiusInKm);
}
