package com.farm.farm_marketplace.controller;

import com.farm.farm_marketplace.Config.JwtUtil;
import com.farm.farm_marketplace.model.User;
import com.farm.farm_marketplace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {

        Optional<User> dbUser = userRepository.findByEmail(user.getEmail());

        if (dbUser.isPresent()) {

            User u = dbUser.get();

            if (passwordEncoder.matches(user.getPassword(), u.getPassword())) {
                return ResponseEntity.ok(jwtUtil.generateToken(u.getEmail(), u.getRole().name()));
            } else {
                throw new RuntimeException("Invalid password");
            }

        } else {
            throw new RuntimeException("User not found");
        }
    }
}