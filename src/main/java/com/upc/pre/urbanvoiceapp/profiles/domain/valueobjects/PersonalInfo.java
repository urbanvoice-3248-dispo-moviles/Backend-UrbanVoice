package com.upc.pre.urbanvoiceapp.profiles.domain.valueobjects;

import java.util.Objects;

/**
 * Value Object que encapsula la información personal del usuario.
 */
public class PersonalInfo {
    private final String name;
    private final String lastName;
    private final int age;

    public PersonalInfo(String name, String lastName, int age) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (age < 13 || age > 150) {
            throw new IllegalArgumentException("Age must be between 13 and 150");
        }
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getFullName() {
        return lastName != null ? name + " " + lastName : name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalInfo that = (PersonalInfo) o;
        return age == that.age && Objects.equals(name, that.name) && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, age);
    }

    @Override
    public String toString() {
        return "PersonalInfo{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}
