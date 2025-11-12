package com.gymapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MealLogRequest {

    // We get user_id from the token (Tip #1)

    private LocalDate logDate; // e.g., "2025-11-13"
    private String mealType; // "Breakfast", "Lunch", "Snack"
    private String foodDescription;
    private Double calories;
    private Double proteinG;
}