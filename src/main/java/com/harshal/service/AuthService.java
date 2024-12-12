package com.harshal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.harshal.config.JwtUtil;
import com.harshal.modal.User;
import com.harshal.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Transactional
    public String registerUser(String email, String password, String role) {
        if (userRepository.findByEmail(email) != null) {
            // Return null if user already exists
            return null;
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);  // Assuming a single role

        // Save user to the database
        userRepository.save(user);

        // Generate and return JWT token with email and roles
        List<String> roles = List.of(user.getRole());  // Assuming `user.getRole()` returns a single role as String
        String token = jwtUtil.generateToken(user.getEmail(), roles);  // Pass roles here
        // Log the token for debugging purposes
        System.out.println("Generated JWT Token: " + token);
        return token;
    }


    public String loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            List<String> roles = List.of(user.getRole());  // If you have roles in your User model
            return jwtUtil.generateToken(user.getEmail(), roles);  // Pass the roles here
        }
        return null;  // Invalid credentials
    }


}
