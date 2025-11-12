package com.gymapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List; // Make sure to import java.util.List
import java.util.Set;

@Data // Creates getters, setters, toString, equals, hashCode
@Builder // Lets you build objects like: Users.builder().email("...").build()
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String password; // We'll store the hash here

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // --- UserDetails Methods ---
    // These are needed for Spring Security.
    // We can hardcode them for now or add a "Role" entity later.

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // For now, we'll return a simple authority.
        // Later, you can have a "roles" table and link it here.
        // e.g., return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.password; // Returns the password_hash
    }

    @Override
    public String getUsername() {
        return this.email; // We are using email as the username
    }

    // You can set these to true for simplicity in Phase 1
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_equipment", // The name of the join table from your SQL
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id")
    )
    private Set<Equipment> availableEquipment = new HashSet<>();

    // --- Many-to-Many Relationship for Allergies ---
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_allergies", // The name of the join table from your SQL
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "allergy_id")
    )
    private Set<Allergy> allergies = new HashSet<>();

    // --- Many-to-Many Relationship for Medical Conditions ---
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_conditions", // The name of the join table from your SQL
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "condition_id")
    )
    private Set<MedicalCondition> medicalConditions = new HashSet<>();

    // --- Many-to-Many Relationship for Disliked Foods ---
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_disliked_foods", // The name of the join table from your SQL
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id")
    )
    private Set<Food> dislikedFoods = new HashSet<>();

    // --- One-to-Many Relationship for Custom Equipment ---
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<CustomEquipment> customEquipment = new HashSet<>();

    // ... your existing UserDetails methods (getAuthorities, etc.) ...
}
