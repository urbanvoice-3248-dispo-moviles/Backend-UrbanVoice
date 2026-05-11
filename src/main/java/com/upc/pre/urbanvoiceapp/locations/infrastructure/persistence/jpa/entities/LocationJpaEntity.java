package com.upc.pre.urbanvoiceapp.locations.infrastructure.persistence.jpa.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * JPA Entity que mapea la tabla 'locations' en la base de datos.
 */
@Entity
@Table(name = "locations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "district")
    private String district;

    @Column(name = "risk_level", nullable = false)
    private Integer riskLevel = 0;

    @Column(name = "risk_description")
    private String riskDescription;

    @Column(name = "incident_count", nullable = false)
    private Integer incidentCount = 0;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
}
