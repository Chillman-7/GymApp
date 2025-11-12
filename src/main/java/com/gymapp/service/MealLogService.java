package com.gymapp.service;

import com.gymapp.dto.MealLogRequest;
import com.gymapp.dto.MealLogResponse;
import com.gymapp.model.MealLog;
import com.gymapp.model.Users;
import com.gymapp.repository.MealLogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealLogService {

    private final MealLogRepository mealLogRepository;

    // Manual Constructor (as requested)
    public MealLogService(MealLogRepository mealLogRepository) {
        this.mealLogRepository = mealLogRepository;
    }

    /**
     * Creates a new meal log for the *currently logged-in user*.
     */
    @Transactional
    public MealLogResponse createMealLog(MealLogRequest request) {
        // Tip #1: Get the user from the security token
        Users currentUser = getAuthenticatedUser();

        MealLog newMealLog = MealLog.builder()
                .user(currentUser)
                .logDate(request.getLogDate())
                .mealType(request.getMealType())
                .foodDescription(request.getFoodDescription())
                .calories(request.getCalories())
                .proteinG(request.getProteinG())
                .build();

        MealLog savedMealLog = mealLogRepository.save(newMealLog);
        return mapEntityToResponse(savedMealLog);
    }

    /**
     * Gets all meal logs for the *currently logged-in user*
     * for a *specific date*.
     */
    @Transactional(readOnly = true)
    public List<MealLogResponse> getMealLogsForDate(LocalDate date) {
        Users currentUser = getAuthenticatedUser();

        return mealLogRepository.findByUser_UserIdAndLogDate(currentUser.getUserId(), date)
                .stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Gets all meal logs for the *currently logged-in user*.
     */
    @Transactional(readOnly = true)
    public List<MealLogResponse> getAllMealLogsForCurrentUser() {
        Users currentUser = getAuthenticatedUser();

        return mealLogRepository.findByUser_UserIdOrderByLogDateAsc(currentUser.getUserId())
                .stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }


    // --- HELPER METHODS ---

    private Users getAuthenticatedUser() {
        return (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private MealLogResponse mapEntityToResponse(MealLog log) {
        return MealLogResponse.builder()
                .mealLogId(log.getMealLogId())
                .userId(log.getUser().getUserId())
                .logDate(log.getLogDate())
                .mealType(log.getMealType())
                .foodDescription(log.getFoodDescription())
                .calories(log.getCalories())
                .proteinG(log.getProteinG())
                .createdAt(log.getCreatedAt())
                .build();
    }
}