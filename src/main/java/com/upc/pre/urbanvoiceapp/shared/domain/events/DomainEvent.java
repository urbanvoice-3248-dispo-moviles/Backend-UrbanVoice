package com.upc.pre.urbanvoiceapp.shared.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Clase base para todos los eventos de dominio en la aplicación.
 * Los domain events representan hechos significativos en el dominio.
 */
public abstract class DomainEvent {
    private final String id;
    private final LocalDateTime occurredAt;

    protected DomainEvent() {
        this.id = UUID.randomUUID().toString();
        this.occurredAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    public abstract String getEventName();
}
