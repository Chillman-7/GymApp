package com.gymapp.config;

import com.gymapp.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    // Manual Constructor (as requested)
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 1. Check if the header is missing or doesn't start with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // If so, pass to the next filter
            return;
        }

        // 2. Extract the token (it's after "Bearer ")
        jwt = authHeader.substring(7);

        // 3. Extract the user's email from the token
        userEmail = jwtService.extractUsername(jwt);

        // 4. Check if we have an email AND the user is not already authenticated
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 5. Load the user from the database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // 6. Check if the token is valid
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // 7. If valid, log the user in by updating the SecurityContext
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // We don't need credentials
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // This is the "login"
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 8. Pass the request to the next filter
        filterChain.doFilter(request, response);
    }
}