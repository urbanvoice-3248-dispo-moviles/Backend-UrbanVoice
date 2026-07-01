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
        var userProfile = userProfileRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), userProfile.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(userProfile.getId(), userProfile.getContactInfo().getEmail());

        LoginResponse response = new LoginResponse(
                userProfile.getId(),
                userProfile.getContactInfo().getEmail(),
                token
        );

        return ResponseEntity.ok(response);
    }
}
