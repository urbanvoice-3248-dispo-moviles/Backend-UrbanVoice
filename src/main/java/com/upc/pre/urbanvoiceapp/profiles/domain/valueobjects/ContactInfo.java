package com.upc.pre.urbanvoiceapp.profiles.domain.valueobjects;

import java.util.Objects;

/**
 * Value Object que encapsula la información de contacto de un usuario.
 */
public class ContactInfo {
    private final String email;
    private final String phoneNumber;

    public ContactInfo(String email, String phoneNumber) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactInfo that = (ContactInfo) o;
        return Objects.equals(email, that.email) && Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, phoneNumber);
    }

    @Override
    public String toString() {
        return "ContactInfo{" +
                "email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
