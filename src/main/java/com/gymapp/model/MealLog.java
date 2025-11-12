package com.gymapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "meal_logs")
public class MealLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_log_id")
    private Long mealLogId;

    // Link back to the user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "log_date", nullable = false)
    private LocalDate logDate;

    @Column(name = "meal_type")
    private String mealType; // e.g., "Breakfast", "Lunch", "Snack"

    @Column(name = "food_description", columnDefinition = "TEXT")
    private String foodDescription;

    @Column(name = "calories")
    private Double calories; // Your SQL is NUMERIC(6,2)

    @Column(name = "protein_g")
    private Double proteinG; // Your SQL is NUMERIC(6,2)

    // Note: Your SQL also has 'created_at', but it's not in this table
    // definition. We'll add it, assuming it's an oversight,
    // as all log tables should have it.
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}