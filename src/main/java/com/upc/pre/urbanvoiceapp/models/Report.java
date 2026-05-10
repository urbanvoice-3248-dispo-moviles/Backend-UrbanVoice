package com.upc.pre.urbanvoiceapp.models;

import com.upc.pre.urbanvoiceapp.shared.documentation.models.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "reports")
public class Report extends AuditableAbstractAggregateRoot<Report> {
    @Getter
    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Getter
    @Setter
    @Column(name = "detail", nullable = true, length = 255)
    private String detail;

    @Getter
    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Getter
    @Column(name = "id_user", nullable = false)
    private Long idUser;

    @Getter
    @Column(name = "image", nullable = true, length = 500)
    private String image;

    @Getter
    @Setter
    @Column(name="address", nullable = false, length = 100)
    private String address;

    public Report(String title, String detail, String type, Long idUser, String image, String address) {
        this.title = title;
        this.detail = detail;
        this.type = type;
        this.idUser = idUser;
        this.image = image;
        this.address = address;
    }

    public Report() {
    }
}
