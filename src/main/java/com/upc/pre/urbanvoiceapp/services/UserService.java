package com.upc.pre.urbanvoiceapp.services;

import com.upc.pre.urbanvoiceapp.models.UserProfile;
import com.upc.pre.urbanvoiceapp.repositories.UserRepository;
import com.upc.pre.urbanvoiceapp.schemas.UpdateUserProfileSchema;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserProfile findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public UserProfile save(UserProfile user) {
        return repository.save(user);
    }

    public UserProfile update(Long id, UpdateUserProfileSchema user) {
        UserProfile existingUser = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        existingUser.setName(user.getName());
        existingUser.setLastname(user.getLastname());
        existingUser.setPhonenumber(user.getPhonenumber());
        existingUser.setProfile_image(user.getProfile_image());
        repository.save(existingUser);
        return existingUser;
    }

    public Optional<UserProfile> findOptionalById(Long id) {
        return repository.findById(id);
    }
}
