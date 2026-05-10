package com.upc.pre.urbanvoiceapp.schemas;

public record UpdateUserPasswordSchema(
        String password
) {
    public String getPassword() { return password; }
}