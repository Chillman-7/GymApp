package com.gymapp.controller;

import com.gymapp.dto.UserSummaryRequest;
import com.gymapp.dto.UserSummaryResponse;
import com.gymapp.service.UserSummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/summary") // Base URL for the AI summary
public class UserSummaryController {

    private final UserSummaryService userSummaryService;

    // Manual Constructor (as requested)
    public UserSummaryController(UserSummaryService userSummaryService) {
        this.userSummaryService = userSummaryService;
    }

    /**
     * GET /api/v1/summary
     * Gets the current AI summary for the authenticated user.
     */
    @GetMapping
    public ResponseEntity<UserSummaryResponse> getSummary() {
        UserSummaryResponse response = userSummaryService.getSummaryForCurrentUser();
        return ResponseEntity.ok(response);
    }

    /**
     * PUT /api/v1/summary
     * Creates or updates the AI summary for the authenticated user.
     */
    @PutMapping
    public ResponseEntity<UserSummaryResponse> createOrUpdateSummary(
            @RequestBody UserSummaryRequest request
    ) {
        UserSummaryResponse response = userSummaryService.createOrUpdateSummary(request);
        return ResponseEntity.ok(response);
    }
}