package com.gymapp.controller;

import com.gymapp.dto.MealLogRequest;
import com.gymapp.dto.MealLogResponse;
import com.gymapp.service.MealLogService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/meal-logs") // Base URL for all meal log endpoints
public class MealLogController {

    private final MealLogService mealLogService;

    // Manual Constructor (as requested)
    public MealLogController(MealLogService mealLogService) {
        this.mealLogService = mealLogService;
    }

    /**
     * POST /api/v1/meal-logs
     * Creates a new meal log entry for the authenticated user.
     */
    @PostMapping
    public ResponseEntity<MealLogResponse> createMealLog(
            @RequestBody MealLogRequest request
    ) {
        MealLogResponse response = mealLogService.createMealLog(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * GET /api/v1/meal-logs/by-date
     * Gets all meal logs for the authenticated user for a specific date.
     * Example: /api/v1/meal-logs/by-date?date=2025-11-13
     */
    @GetMapping("/by-date")
    public ResponseEntity<List<MealLogResponse>> getMealLogsByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<MealLogResponse> logs = mealLogService.getMealLogsForDate(date);
        return ResponseEntity.ok(logs);
    }

    /**
     * GET /api/v1/meal-logs/all
     * Gets all meal logs for the authenticated user.
     */
    @GetMapping("/all")
    public ResponseEntity<List<MealLogResponse>> getAllMealLogs() {
        List<MealLogResponse> logs = mealLogService.getAllMealLogsForCurrentUser();
        return ResponseEntity.ok(logs);
    }
}