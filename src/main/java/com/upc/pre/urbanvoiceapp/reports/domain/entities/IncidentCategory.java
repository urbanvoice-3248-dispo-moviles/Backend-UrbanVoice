package com.upc.pre.urbanvoiceapp.reports.domain.entities;

import lombok.Getter;

@Getter
public class IncidentCategory {
    private final Long id;
    private String name;
    private String description;

    public IncidentCategory(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public IncidentCategory(String name, String description) {
        this(null, name, description);
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Category name cannot be null or blank");
        }
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
