package com.upc.pre.urbanvoiceapp.controllers;

import com.upc.pre.urbanvoiceapp.models.UserProfile;
import com.upc.pre.urbanvoiceapp.schemas.UpdateUserProfileSchema;
import com.upc.pre.urbanvoiceapp.schemas.UserProfileSchema;
import com.upc.pre.urbanvoiceapp.security.iam.domain.services.UserIAMCommandService;
import com.upc.pre.urbanvoiceapp.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "Users API")
public class UserController {
    private final UserService userService;
    private final UserIAMCommandService userIAMCommandService;

    public UserController(UserService userService, UserIAMCommandService userIAMCommandService) {
        this.userService = userService;
        this.userIAMCommandService = userIAMCommandService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        try {
            UserProfile user = userService.findByEmail(email);
            if(user == null) {
                return ResponseEntity.badRequest().body("User not found");
            }
            return ResponseEntity.ok(user);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserByEmail(@PathVariable Long id) {
        try {
            userService.deleteById(id);
            userIAMCommandService.deleteById(id);

            return ResponseEntity.ok("User deleted in both tables");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserProfileSchema user) {
        try {
            UserProfile newUser = new UserProfile(
                    user.name(),
                    user.email(),
                    user.password(),
                    user.lastname(),
                    user.phonenumber(),
                    user.user_id(),
                    user.profile_image());
            UserProfile createdUser = userService.save(newUser);
            return ResponseEntity.ok(createdUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UpdateUserProfileSchema user) {
        try {
            UserProfile updatedUser = userService.update(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}