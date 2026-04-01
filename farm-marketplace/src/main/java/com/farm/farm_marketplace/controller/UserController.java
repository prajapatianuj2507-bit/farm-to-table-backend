package com.farm.farm_marketplace.controller;

import com.farm.farm_marketplace.model.User;
import com.farm.farm_marketplace.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Register user
    @PostMapping
    public User registerUser(@Valid @RequestBody User user) {
        return userService.registerUser(user);
    }

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}