package com.gymapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "custom_equipment")
public class CustomEquipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_equipment_id")
    private Long customEquipmentId;

    // --- THIS IS THE FIX ---
    // Change 'UserProfile userProfile' to 'Users user'
    // Update @JoinColumn to point to "user_id"
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(nullable = false)
    private String name;
}