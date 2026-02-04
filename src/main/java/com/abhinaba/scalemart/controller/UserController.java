package com.abhinaba.scalemart.controller;

import com.abhinaba.scalemart.dto.UserDTO;
import com.abhinaba.scalemart.model.Users;
import com.abhinaba.scalemart.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
        try {
            Users newUser = userService.registerUser(userDTO);
            return ResponseEntity.ok("User registered successfully via ID: " + newUser.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Users> searchUser(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String username) {
        
        if (email != null) {
            return ResponseEntity.ok(userService.getUserByEmail(email));
        } else if (username != null) {
            return ResponseEntity.ok(userService.getUserByUsername(username));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}