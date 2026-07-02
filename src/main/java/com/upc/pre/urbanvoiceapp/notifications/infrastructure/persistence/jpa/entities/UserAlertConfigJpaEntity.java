package com.upc.pre.urbanvoiceapp.notifications.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_alert_configs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAlertConfigJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @Column(name = "radius_in_km")
    private double radiusInKm = 5.0;

    @Column(name = "notify_by_email")
    private boolean notifyByEmail = false;
}
