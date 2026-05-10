package com.upc.pre.urbanvoiceapp.models;

import com.upc.pre.urbanvoiceapp.shared.documentation.models.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "locations")
public class Location extends AuditableAbstractAggregateRoot<Location> {
    @Getter
    @Setter
    @Column(name="latitude", nullable = false, length = 30)
    private String aLatitude;

    @Getter
    @Setter
    @Column(name="longitude", nullable = false, length = 30)
    private String aLongitude;

    @Getter
    @Setter
    @Column(name="id_report", nullable = false)
    private Long idReport;

    public Location(String aLatitude, String aLongitude, Long idReport) {
        this.aLatitude = aLatitude;
        this.aLongitude = aLongitude;
        this.idReport = idReport;
    }

    public Location() {
    }
}
