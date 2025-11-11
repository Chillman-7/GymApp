package com.gymapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;

    // Note: We'll add all the UserProfile fields here later
    // (e.g., LocalDate dateOfBirth, Gender gender, etc.)
    // For now, email/password is enough to create the user.
}