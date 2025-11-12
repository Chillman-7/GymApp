package com.gymapp.repository;

import com.gymapp.model.UserSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSummaryRepository extends JpaRepository<UserSummary, Long> {

    /**
     * Finds the summary for a specific user.
     * (Using our correct "User_UserId" naming)
     */
    Optional<UserSummary> findByUser_UserId(Long userId);

}