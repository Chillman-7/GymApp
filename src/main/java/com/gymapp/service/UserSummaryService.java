package com.gymapp.service;

import com.gymapp.dto.UserSummaryRequest;
import com.gymapp.dto.UserSummaryResponse;
import com.gymapp.model.UserSummary;
import com.gymapp.model.Users;
import com.gymapp.repository.UserSummaryRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserSummaryService {

    private final UserSummaryRepository userSummaryRepository;

    // Manual Constructor (as requested)
    public UserSummaryService(UserSummaryRepository userSummaryRepository) {
        this.userSummaryRepository = userSummaryRepository;
    }

    /**
     * Gets the AI summary for the *currently logged-in user*.
     */
    @Transactional(readOnly = true)
    public UserSummaryResponse getSummaryForCurrentUser() {
        Users currentUser = getAuthenticatedUser();

        return userSummaryRepository.findByUser_UserId(currentUser.getUserId())
                .map(this::mapEntityToResponse)
                .orElse(null); // Return null if no summary exists yet
    }

    /**
     * Creates a new summary or updates an existing one
     * for the *currently logged-in user*.
     */
    @Transactional
    public UserSummaryResponse createOrUpdateSummary(UserSummaryRequest request) {
        Users currentUser = getAuthenticatedUser();

        // Find the existing summary, or create a new one
        Optional<UserSummary> existingSummaryOpt =
                userSummaryRepository.findByUser_UserId(currentUser.getUserId());

        UserSummary summaryToSave;

        if (existingSummaryOpt.isPresent()) {
            // --- UPDATE ---
            summaryToSave = existingSummaryOpt.get();
            summaryToSave.setSummaryText(request.getSummaryText());
            // 'lastUpdated' will be handled automatically by @UpdateTimestamp
        } else {
            // --- CREATE ---
            summaryToSave = UserSummary.builder()
                    .user(currentUser)
                    .summaryText(request.getSummaryText())
                    .build();
        }

        UserSummary savedSummary = userSummaryRepository.save(summaryToSave);
        return mapEntityToResponse(savedSummary);
    }


    // --- HELPER METHODS ---

    private Users getAuthenticatedUser() {
        return (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private UserSummaryResponse mapEntityToResponse(UserSummary summary) {
        return UserSummaryResponse.builder()
                .summaryId(summary.getSummaryId())
                .userId(summary.getUser().getUserId())
                .summaryText(summary.getSummaryText())
                .lastUpdated(summary.getLastUpdated())
                .build();
    }
}