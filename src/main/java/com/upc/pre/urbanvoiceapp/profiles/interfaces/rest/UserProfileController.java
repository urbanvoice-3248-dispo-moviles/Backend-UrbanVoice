package com.upc.pre.urbanvoiceapp.profiles.interfaces.rest;

import com.upc.pre.urbanvoiceapp.profiles.application.commands.CreateUserProfileCommand;
import com.upc.pre.urbanvoiceapp.profiles.application.commands.DeleteUserProfileCommand;
import com.upc.pre.urbanvoiceapp.profiles.application.commands.UpdateUserProfileCommand;
import com.upc.pre.urbanvoiceapp.profiles.application.queries.GetUserProfileByEmailQuery;
import com.upc.pre.urbanvoiceapp.profiles.application.queries.GetUserProfileByIdQuery;
import com.upc.pre.urbanvoiceapp.profiles.application.services.UserProfileApplicationService;
import com.upc.pre.urbanvoiceapp.profiles.interfaces.rest.assemblers.UserProfileAssembler;
import com.upc.pre.urbanvoiceapp.profiles.interfaces.rest.resources.CreateUserProfileResource;
import com.upc.pre.urbanvoiceapp.profiles.interfaces.rest.resources.UpdateUserProfileResource;
import com.upc.pre.urbanvoiceapp.profiles.interfaces.rest.resources.UserProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller para gestionar perfiles de usuario.
 * Endpoints: /api/v1/profiles
 */
@RestController
@RequestMapping("/api/v1/profiles")
@Tag(name = "User Profiles", description = "API para gestionar perfiles de usuario")
public class UserProfileController {

    private final UserProfileApplicationService userProfileService;
    private final UserProfileAssembler userProfileAssembler;

    public UserProfileController(UserProfileApplicationService userProfileService,
                                UserProfileAssembler userProfileAssembler) {
        this.userProfileService = userProfileService;
        this.userProfileAssembler = userProfileAssembler;
    }

    @PostMapping
    @Operation(summary = "Crear nuevo perfil de usuario")
    public ResponseEntity<UserProfileResponse> createUserProfile(@RequestBody CreateUserProfileResource resource) {
        CreateUserProfileCommand command = userProfileAssembler.toCreateCommand(resource);
        var userProfile = userProfileService.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userProfileAssembler.toResponse(userProfile));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener perfil de usuario por ID")
    public ResponseEntity<UserProfileResponse> getUserProfileById(@PathVariable Long id) {
        GetUserProfileByIdQuery query = new GetUserProfileByIdQuery(id);
        var userProfile = userProfileService.handle(query);
        return ResponseEntity.ok(userProfileAssembler.toResponse(userProfile));
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Obtener perfil de usuario por email")
    public ResponseEntity<UserProfileResponse> getUserProfileByEmail(@PathVariable String email) {
        GetUserProfileByEmailQuery query = new GetUserProfileByEmailQuery(email);
        var userProfile = userProfileService.handle(query);
        return ResponseEntity.ok(userProfileAssembler.toResponse(userProfile));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar perfil de usuario")
    public ResponseEntity<UserProfileResponse> updateUserProfile(
            @PathVariable Long id,
            @RequestBody UpdateUserProfileResource resource) {
        UpdateUserProfileCommand command = userProfileAssembler.toUpdateCommand(id, resource);
        var userProfile = userProfileService.handle(command);
        return ResponseEntity.ok(userProfileAssembler.toResponse(userProfile));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar perfil de usuario")
    public ResponseEntity<Void> deleteUserProfile(@PathVariable Long id) {
        DeleteUserProfileCommand command = new DeleteUserProfileCommand(id);
        userProfileService.handle(command);
        return ResponseEntity.noContent().build();
    }
}
