package com.gymapp.repository;

import com.gymapp.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    // This will be a very useful method to find a profile
    // based on the main user's ID.
    Optional<UserProfile> findByUser_UserId(Long userId);
}