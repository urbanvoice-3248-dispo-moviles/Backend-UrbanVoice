package com.upc.pre.urbanvoiceapp.profiles.domain.events;

import com.upc.pre.urbanvoiceapp.shared.domain.events.DomainEvent;

public class UserProfileCreatedEvent extends DomainEvent {
    private final Long userId;
    private final String email;
    private final String name;

    public UserProfileCreatedEvent(Long userId, String email, String name) {
        super();
        this.userId = userId;
        this.email = email;
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getEventName() {
        return "UserProfileCreated";
    }
}
