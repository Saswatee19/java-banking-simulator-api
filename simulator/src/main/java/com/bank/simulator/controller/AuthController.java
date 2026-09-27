package com.bank.simulator.controller;

import com.bank.simulator.dto.RegisterRequest;
import com.bank.simulator.entity.User;
import com.bank.simulator.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        // CRITICAL: Hash the password using BCrypt before storing it in the database
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        String userRole = (request.getRole() != null) ? request.getRole().toUpperCase() : "USER";

        User newUser = new User(request.getUsername(), encodedPassword, "ROLE_" + userRole);
        userRepository.save(newUser);

        return ResponseEntity.ok("User registered successfully!");
    }
}