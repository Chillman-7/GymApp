package com.gymapp.service;

import com.gymapp.dto.AuthResponse;
import com.gymapp.dto.LoginRequest;
import com.gymapp.dto.RegisterRequest;
import com.gymapp.model.Users;
import com.gymapp.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Manual Constructor (as requested)
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Registers a new user and returns a JWT.
     */
    public AuthResponse register(RegisterRequest request) {
        // 1. Create a new Users object
        Users user = Users.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                // We'll add roles later, for now, it's a simple user
                .build();

        // 2. Save the user to the database
        Users savedUser = userRepository.save(user);

        // TODO: We also need to create and save their UserProfile
        // using the other fields from the RegisterRequest.

        // 3. Generate a JWT for the new user
        String jwtToken = jwtService.generateToken(savedUser);

        // 4. Return the AuthResponse
        return AuthResponse.builder()
                .token(jwtToken)
                .email(savedUser.getEmail())
                .userId(savedUser.getUserId())
                .build();
    }

    /**
     * Authenticates an existing user and returns a JWT.
     */
    public AuthResponse login(LoginRequest request) {
        // 1. Authenticate the user (throws an exception if details are wrong)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2. If authentication is successful, find the user
        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found after authentication"));

        // 3. Generate a JWT
        String jwtToken = jwtService.generateToken(user);

        // 4. Return the AuthResponse
        return AuthResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .userId(user.getUserId())
                .build();
    }
}