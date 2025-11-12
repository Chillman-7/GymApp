package com.gymapp.dto;

import com.gymapp.model.enums.*; // Import your enums package
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    // 1. For the 'users' table
    private String email;
    private String password;

    // 2. For the 'user_profiles' table
    private LocalDate dateOfBirth;
    private Double heightCm;
    private Double weightKg; // Your SQL has this, let's add it
    private Gender gender;
    private PrimaryGoal primaryGoal;
    private ExperienceLevel experienceLevel;
    private DietaryType dietaryType;
    private Double bodyFatPercentage;
    private ActivityLevel activityLevel;
    private WorkoutLocation workoutLocation;
    private Integer mealFrequency; // Your SQL has this
    private Integer cookingTimeMinutes; // Your SQL has this

    // 3. For the "List" relationships
    // We just need the list of IDs from the frontend

    private Set<Long> equipmentIds; // e.g., [1, 5, 8]
    private Set<Long> allergyIds;
    private Set<Long> medicalConditionIds;
    private Set<Long> dislikedFoodIds;

    // For custom equipment
    private Set<String> customEquipmentNames; // e.g., ["TRX Straps", "20kg Kettlebell"]
}