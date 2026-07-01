package com.upc.pre.urbanvoiceapp.reports.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * JPA Entity que mapea la tabla {@code incident_reports} en la base de datos.
 *
 * <p>Esta clase pertenece a infraestructura y no debe contener reglas de
 * negocio; esas reglas viven en el agregado de dominio {@code IncidentReport}.</p>
 */
@Entity
@Table(name = "incident_reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class IncidentReportJpaEntity {
    /** Identificador primario autogenerado del reporte. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Identificador del usuario que creo el reporte. */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /** Titulo breve del incidente. */
    @Column(name = "title", nullable = false)
    private String title;

    /** Descripcion detallada del incidente. */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /** Codigo del tipo de incidente. */
    @Column(name = "incident_type", nullable = false)
    private String incidentType;

    /** Latitud del incidente. */
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    /** Longitud del incidente. */
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    /** Direccion textual asociada a la ubicacion. */
    @Column(name = "address")
    private String address;

    /** URL de evidencia multimedia asociada al reporte. */
    @Column(name = "media_url")
    private String mediaUrl;

    /** Indica si el reporte fue registrado como anonimo. */
    @Column(name = "is_anonymous", nullable = false)
    private Boolean isAnonymous = false;

    /** Fecha de creacion auditada del reporte. */
    @CreatedDate
    @Column(name = "reported_at", nullable = false, updatable = false)
    private LocalDateTime reportedAt;
}
