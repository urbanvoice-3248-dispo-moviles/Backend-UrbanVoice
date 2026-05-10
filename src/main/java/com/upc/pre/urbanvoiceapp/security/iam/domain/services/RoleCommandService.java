package com.upc.pre.urbanvoiceapp.security.iam.domain.services;


import com.upc.pre.urbanvoiceapp.security.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}
