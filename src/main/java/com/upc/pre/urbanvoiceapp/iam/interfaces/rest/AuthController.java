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

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "API de autenticación con JWT")
public class AuthController {

    private final UserProfileRepository userProfileRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final Map<String, String> passwordResetTokens = new ConcurrentHashMap<>();

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

    @PostMapping("/forgot-password")
    @Operation(summary = "Solicitar restablecimiento de contraseña")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        var userProfile = userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Email not found"));

        String resetToken = UUID.randomUUID().toString();
        passwordResetTokens.put(resetToken, email);

        return ResponseEntity.ok(Map.of("message", "Password reset token generated", "token", resetToken));
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Restablecer contraseña con token")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("new_password");

        String email = passwordResetTokens.remove(token);
        if (email == null) {
            throw new IllegalArgumentException("Invalid or expired reset token");
        }

        var userProfile = userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Email not found"));

        userProfile.setPassword(passwordEncoder.encode(newPassword));
        userProfileRepository.save(userProfile);

        return ResponseEntity.ok(Map.of("message", "Password reset successfully"));
    }
}
