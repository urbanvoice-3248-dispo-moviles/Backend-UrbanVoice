package com.upc.pre.urbanvoiceapp.security.iam.interfaces.rest;

import com.upc.pre.urbanvoiceapp.security.iam.domain.services.UserIAMCommandService;
import com.upc.pre.urbanvoiceapp.security.iam.interfaces.rest.resources.ChangePasswordResource;
import com.upc.pre.urbanvoiceapp.security.iam.interfaces.rest.resources.SignInResource;
import com.upc.pre.urbanvoiceapp.security.iam.interfaces.rest.resources.SignUpResource;
import com.upc.pre.urbanvoiceapp.security.iam.interfaces.rest.transform.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AuthenticationController
 * <p>
 * This controller is responsible for handling authentication requests.
 * It exposes two endpoints:
 *     <ul>
 *         <li>POST /api/v1/authentication/sign-in</li>
 *         <li>POST /api/v1/authentication/sign-up</li>
 *     </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Authentication Endpoints")
public class AuthIAMController {
    private final UserIAMCommandService userCommandService;

    public AuthIAMController(UserIAMCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    /**
     * Handles the sign-in request.
     *
     * @param signInResource the sign-in request body.
     * @return the authenticated user resource.
     */
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInResource signInResource) {
        try {
            var signInCommand = SignInCommandFromResourceAssembler.toCommandFromResource(signInResource);
            var authenticatedUser = userCommandService.handle(signInCommand);
            if (authenticatedUser.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(authenticatedUser.get().getLeft(), authenticatedUser.get().getRight());
            return ResponseEntity.ok(authenticatedUserResource);
        } catch (Exception e) {
            var bodyJSON = "{\"message\": \"" + e.getMessage() + "\"}";
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(bodyJSON);
        }
    }

    /**
     * Handles the sign-up request.
     *
     * @param signUpResource the sign-up request body.
     * @return the created user resource.
     */
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpResource signUpResource) {
        try {
            var signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromResource(signUpResource);
            var user = userCommandService.handle(signUpCommand);
            if (user.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
            return new ResponseEntity<>(userResource, HttpStatus.CREATED);
        } catch (Exception e) {
            var bodyJSON = "{\"message\": \"" + e.getMessage() + "\"}";
            return ResponseEntity.ok().body(bodyJSON);
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordResource changePasswordResource) {
        try {
            var changePasswordCommand = changePasswordCommandFromResourceAssembler.toCommandFromResource(changePasswordResource);
            var user = userCommandService.handle(changePasswordCommand);
            if (user.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
            return new ResponseEntity<>(userResource, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            var bodyJSON = "{\"message\": \"" + e.getMessage() + "\"}";
            return ResponseEntity.ok().body(bodyJSON);
        }
    }

}
