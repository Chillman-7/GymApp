package com.gymapp.repository;

import com.gymapp.model.CustomEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomEquipmentRepository extends JpaRepository<CustomEquipment, Long> {

    // --- THIS IS THE FIX ---
    //
    // Old name: findByUserProfile_ProfileId(Long profileId)
    //
    // New name: findByUser_UserId(Long userId)
    //
    // This tells Spring: "Find by the 'user' object's 'userId' property."
    List<CustomEquipment> findByUser_UserId(Long userId);
}