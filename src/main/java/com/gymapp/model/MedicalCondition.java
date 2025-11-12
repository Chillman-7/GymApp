package com.gymapp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "medical_conditions")
public class MedicalCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "condition_id") // Your SQL uses condition_id
    private Long medicalConditionId;

    @Column(unique = true, nullable = false)
    private String name;

    // --- THIS IS THE FIX ---
    // Change 'mappedBy' to "medicalConditions"
    // Change 'Set<UserProfile> userProfiles' to 'Set<Users> users'
    @ManyToMany(mappedBy = "medicalConditions")
    private Set<Users> users = new HashSet<>();
}