package com.upc.pre.urbanvoiceapp.schemas;

public record UpdateUserProfileSchema (
        String name,
        String lastname,
        String phonenumber,
        String profile_image
) {
    public String getName() { return name; }
    public String getLastname() { return lastname; }
    public String getPhonenumber() { return phonenumber; }
    public String getProfile_image() { return profile_image; }
}