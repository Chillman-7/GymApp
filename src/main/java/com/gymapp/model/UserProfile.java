package com.gymapp.model;

import com.gymapp.model.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long profileId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users user;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "height_cm")
    private Double heightCm;

    // --- ADD THESE 4 MISSING FIELDS ---
    @Column(name = "weight_kg")
    private Double weightKg; // This will fix your immediate error

    @Column(name = "body_fat_percentage")
    private Double bodyFatPercentage;

    @Column(name = "meal_frequency")
    private Integer mealFrequency;

    @Column(name = "cooking_time_minutes")
    private Integer cookingTimeMinutes;
    // --- END OF ADDITIONS ---

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "primary_goal")
    private PrimaryGoal primaryGoal;

    @Enumerated(EnumType.STRING)
    @Column(name = "experience_level")
    private ExperienceLevel experienceLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "dietary_type")
    private DietaryType dietaryType;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_level")
    private ActivityLevel activityLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "workout_location")
    private WorkoutLocation workoutLocation;
}