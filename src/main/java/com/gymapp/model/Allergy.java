package com.gymapp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "allergies")
public class Allergy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "allergy_id")
    private Long allergyId;

    @Column(unique = true, nullable = false)
    private String name;

    // --- THIS IS THE FIX ---
    // Change 'mappedBy' to "allergies" (to match the field in Users.java)
    // Change 'Set<UserProfile> userProfiles' to 'Set<Users> users'
    @ManyToMany(mappedBy = "allergies")
    private Set<Users> users = new HashSet<>();
}