package com.project.telemedicine.controller;

import com.project.telemedicine.model.User;
import com.project.telemedicine.service.AuthService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(authService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest, HttpSession session) {
        User authenticatedUser = authService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());

        // Save user in the session
        session.setAttribute("user", authenticatedUser);

        return ResponseEntity.ok("Login successful. Welcome, " + authenticatedUser.getName() + "!");
    }

    @GetMapping("/users/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String role) {
        return ResponseEntity.ok(authService.getUsersByRole(role));
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        if (session != null) {
            session.invalidate(); // Invalidate the session to log the user out
        }
        return ResponseEntity.ok("Logout successful");
    }


}
