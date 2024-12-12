package com.harshal.controller;

import com.harshal.modal.User;
import com.harshal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Get user role based on JWT
    @GetMapping("/role")
    public ResponseEntity<User> getUserRole() {
        User user = userService.getAuthenticatedUser();
        return ResponseEntity.ok(user);
    }

    // Update user information (if needed)
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }
}

