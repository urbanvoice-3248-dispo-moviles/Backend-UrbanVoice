package com.upc.pre.urbanvoiceapp.security.iam.infrastructure.tokens.jwt;

import com.upc.pre.urbanvoiceapp.security.iam.application.internal.outboundservices.tokens.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface BearerTokenService extends TokenService {

    String getBearerTokenFrom(HttpServletRequest request);
    String generateToken(Authentication authentication);
}

