package com.gymapp.controller;

import com.gymapp.dto.AuthResponse;
import com.gymapp.dto.LoginRequest;
import com.gymapp.dto.RegisterRequest;
import com.gymapp.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth") // Base URL for all auth endpoints
public class AuthController {

    private final AuthService authService;

    // Manual Constructor (as requested)
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * POST /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }
}