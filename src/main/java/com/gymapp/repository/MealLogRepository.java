package com.gymapp.repository;

import com.gymapp.model.MealLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealLogRepository extends JpaRepository<MealLog, Long> {

    /**
     * Finds all meal logs for a user, ordered by date.
     */
    List<MealLog> findByUser_UserIdOrderByLogDateAsc(Long userId);

    /**
     * Finds all meal logs for a user on a specific date.
     */
    List<MealLog> findByUser_UserIdAndLogDate(Long userId, LocalDate logDate);

}