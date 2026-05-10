package com.upc.pre.urbanvoiceapp.security.iam.domain.services;


import com.upc.pre.urbanvoiceapp.security.iam.domain.model.aggregates.User;
import com.upc.pre.urbanvoiceapp.security.iam.domain.model.commands.ChangePasswordCommand;
import com.upc.pre.urbanvoiceapp.security.iam.domain.model.commands.SignInCommand;
import com.upc.pre.urbanvoiceapp.security.iam.domain.model.commands.SignUpCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public interface UserIAMCommandService {
    Optional<User> handle(SignUpCommand command);
    Optional<User> handle(ChangePasswordCommand command);
    Optional<ImmutablePair<User, String>> handle(SignInCommand command);
    // delete user
    void deleteById(Long id);
}
