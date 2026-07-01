package com.upc.pre.urbanvoiceapp.iam.interfaces.rest;

import com.upc.pre.urbanvoiceapp.iam.infrastructure.security.jwt.JwtUtil;
import com.upc.pre.urbanvoiceapp.iam.interfaces.rest.resources.LoginRequest;
import com.upc.pre.urbanvoiceapp.iam.interfaces.rest.resources.LoginResponse;
import com.upc.pre.urbanvoiceapp.profiles.domain.repositories.UserProfileRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "API de autenticación con JWT")
public class AuthController {

    private final UserProfileRepository userProfileRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserProfileRepository userProfileRepository,
                          BCryptPasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión y obtener token JWT")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            var userProfile = userProfileRepository.findByEmail(request.getEmail())
                    .orElse(null);

            if (userProfile == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("Invalid email or password"));
            }

            if (!passwordEncoder.matches(request.getPassword(), userProfile.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("Invalid email or password"));
            }

            String token = jwtUtil.generateToken(userProfile.getId(), userProfile.getContactInfo().getEmail());

            LoginResponse response = new LoginResponse(
                    userProfile.getId(),
                    userProfile.getContactInfo().getEmail(),
                    token
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    private static class ErrorResponse {
        private final String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
