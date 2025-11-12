package com.gymapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MealLogResponse {

    private Long mealLogId;
    private Long userId;
    private LocalDate logDate;
    private String mealType;
    private String foodDescription;
    private Double calories;
    private Double proteinG;
    private LocalDateTime createdAt;
}