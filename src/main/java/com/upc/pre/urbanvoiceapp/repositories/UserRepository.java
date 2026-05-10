package com.upc.pre.urbanvoiceapp.repositories;

import com.upc.pre.urbanvoiceapp.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserProfile, Long> {
    @Query("SELECT u FROM UserProfile u WHERE u.email = ?1")
    UserProfile findByEmail(String email);

    // delete user by email
    @Modifying
    @Query("DELETE FROM UserProfile u WHERE u.user_id = ?1")
    void deleteById(Long id);
}