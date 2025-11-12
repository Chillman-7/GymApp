package com.gymapp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "foods")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long foodId;

    @Column(unique = true, nullable = false)
    private String name;

    // --- THIS IS THE FIX ---
    // Change 'mappedBy' to "dislikedFoods"
    // Change 'Set<UserProfile> userProfiles' to 'Set<Users> users'
    @ManyToMany(mappedBy = "dislikedFoods")
    private Set<Users> users = new HashSet<>();
}