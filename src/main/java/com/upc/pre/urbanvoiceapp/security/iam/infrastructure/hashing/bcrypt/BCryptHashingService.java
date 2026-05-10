package com.upc.pre.urbanvoiceapp.security.iam.infrastructure.hashing.bcrypt;

import com.upc.pre.urbanvoiceapp.security.iam.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface BCryptHashingService extends HashingService, PasswordEncoder {

}