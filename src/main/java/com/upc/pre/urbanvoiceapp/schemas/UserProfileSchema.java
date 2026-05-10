package com.upc.pre.urbanvoiceapp.schemas;

public record UserProfileSchema(
        String name,
        String lastname,
        String phonenumber,
        String email,
        String password,
        String user_id,
        String profile_image) {
}
