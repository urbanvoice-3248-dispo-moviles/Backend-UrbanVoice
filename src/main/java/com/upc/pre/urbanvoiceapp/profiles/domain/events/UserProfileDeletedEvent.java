package com.upc.pre.urbanvoiceapp.profiles.domain.events;

import com.upc.pre.urbanvoiceapp.shared.domain.events.DomainEvent;

public class UserProfileDeletedEvent extends DomainEvent {
    private final Long userId;
    private final String email;

    public UserProfileDeletedEvent(Long userId, String email) {
        super();
        this.userId = userId;
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getEventName() {
        return "UserProfileDeleted";
    }
}
