package com.upc.pre.urbanvoiceapp.profiles.domain.events;

import com.upc.pre.urbanvoiceapp.shared.domain.events.DomainEvent;

public class UserProfileUpdatedEvent extends DomainEvent {
    private final Long userId;
    private final String name;
    private final String email;

    public UserProfileUpdatedEvent(Long userId, String name, String email) {
        super();
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getEventName() {
        return "UserProfileUpdated";
    }
}
