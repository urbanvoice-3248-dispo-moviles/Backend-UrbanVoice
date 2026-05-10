package com.upc.pre.urbanvoiceapp.security.iam.interfaces.rest.transform;

import com.upc.pre.urbanvoiceapp.security.iam.domain.model.commands.ChangePasswordCommand;
import com.upc.pre.urbanvoiceapp.security.iam.interfaces.rest.resources.ChangePasswordResource;

public class changePasswordCommandFromResourceAssembler {
    public static ChangePasswordCommand toCommandFromResource(ChangePasswordResource resource) {
        return new ChangePasswordCommand(resource.username(), resource.password());
    }
}
