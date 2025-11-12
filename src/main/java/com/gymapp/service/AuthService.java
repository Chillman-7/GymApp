package com.gymapp.service;

import com.gymapp.dto.AuthResponse;
import com.gymapp.dto.LoginRequest;
import com.gymapp.dto.RegisterRequest;
import com.gymapp.model.*; // Import all models
import com.gymapp.repository.*; // Import all repositories
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import this!

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    // --- All of our required "tools" ---
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // --- NEW "tools" for Phase 3.5 ---
    private final UserProfileRepository userProfileRepository;
    private final AllergyRepository allergyRepository;
    private final MedicalConditionRepository medicalConditionRepository;
    private final EquipmentRepository equipmentRepository;
    private final FoodRepository foodRepository;

    // --- The new, larger manual constructor ---
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager,
                       UserProfileRepository userProfileRepository, // new
                       AllergyRepository allergyRepository, // new
                       MedicalConditionRepository medicalConditionRepository, // new
                       EquipmentRepository equipmentRepository, // new
                       FoodRepository foodRepository) { // new
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userProfileRepository = userProfileRepository;
        this.allergyRepository = allergyRepository;
        this.medicalConditionRepository = medicalConditionRepository;
        this.equipmentRepository = equipmentRepository;
        this.foodRepository = foodRepository;
    }

    /**
     * Registers a new user WITH their full profile.
     * This is a @Transactional operation. If *any* part fails
     * (e.g., saving the profile), the entire operation will be
     * rolled back, and the user will not be created.
     */
    @Transactional(rollbackFor = Exception.class) // This is critical
    public AuthResponse register(RegisterRequest request) {

        // 1. Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already taken");
        }

        // 2. Fetch all the "List" entities from the DB
        // We do this by finding all entities by their IDs
        Set<Allergy> allergies = new HashSet<>();
        if (request.getAllergyIds() != null) {
            allergies = new HashSet<>(allergyRepository.findAllById(request.getAllergyIds()));
        }

        Set<MedicalCondition> medicalConditions = new HashSet<>();
        if (request.getMedicalConditionIds() != null) {
            medicalConditions = new HashSet<>(medicalConditionRepository.findAllById(request.getMedicalConditionIds()));
        }

        Set<Equipment> equipment = new HashSet<>();
        if (request.getEquipmentIds() != null) {
            equipment = new HashSet<>(equipmentRepository.findAllById(request.getEquipmentIds()));
        }

        Set<Food> dislikedFoods = new HashSet<>();
        if (request.getDislikedFoodIds() != null) {
            dislikedFoods = new HashSet<>(foodRepository.findAllById(request.getDislikedFoodIds()));
        }

        // 3. Create the main 'Users' entity
        Users user = Users.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                // Assign all the lists we just fetched
                .allergies(allergies)
                .medicalConditions(medicalConditions)
                .availableEquipment(equipment)
                .dislikedFoods(dislikedFoods)
                .build();

        // 4. Handle 'Custom Equipment' (One-to-Many)
        // We create new objects and link them to the user
        if (request.getCustomEquipmentNames() != null) {
            request.getCustomEquipmentNames().forEach(name -> {
                // This assumes your CustomEquipment model has a constructor
                // or that we correctly set the 'user' link
                // Let's ensure the link is set
                CustomEquipment ce = new CustomEquipment();
                ce.setUser(user); // Link to the user
                ce.setName(name);
                user.getCustomEquipment().add(ce); // Add to the set
            });
        }

        // 5. Save the 'Users' entity (and its 'cascaded' custom equipment)
        Users savedUser = userRepository.save(user);

        // 6. Create and save the 'UserProfile' entity
        UserProfile userProfile = UserProfile.builder()
                .user(savedUser) // Link to the user we just saved
                .dateOfBirth(request.getDateOfBirth())
                .heightCm(request.getHeightCm())
                .weightKg(request.getWeightKg())
                .bodyFatPercentage(request.getBodyFatPercentage())
                .gender(request.getGender())
                .primaryGoal(request.getPrimaryGoal())
                .experienceLevel(request.getExperienceLevel())
                .dietaryType(request.getDietaryType())
                .activityLevel(request.getActivityLevel())
                .workoutLocation(request.getWorkoutLocation())
                .mealFrequency(request.getMealFrequency())
                .cookingTimeMinutes(request.getCookingTimeMinutes())
                .build();

        userProfileRepository.save(userProfile);

        // 7. Generate a JWT for the new user
        String jwtToken = jwtService.generateToken(savedUser);

        // 8. Return the AuthResponse
        return AuthResponse.builder()
                .token(jwtToken)
                .email(savedUser.getEmail())
                .userId(savedUser.getUserId())
                .build();
    }

    /**
     * Authenticates an existing user and returns a JWT.
     * (This method remains unchanged)
     */
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found after authentication"));

        String jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .userId(user.getUserId())
                .build();
    }
}