package com.project.telemedicine.controller;

import com.project.telemedicine.model.User;
import com.project.telemedicine.repository.UserRepository;
import com.project.telemedicine.service.AuthService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(authService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            @RequestBody(required = false) Map<String, String> body,
            HttpSession session) {

        // ✅ If request parameters are missing, fetch from JSON body
        if (email == null && body != null) {
            email = body.get("email");
            password = body.get("password");
        }

        // ✅ Check if email and password are provided
        if (email == null || password == null) {
            return ResponseEntity.badRequest().body("Email and password are required");
        }

        // ✅ Fetch user from database
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return ResponseEntity.status(404).body("User not found with email: " + email);
        }

        // ✅ Compare hashed password using passwordEncoder.matches()
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        // ✅ Store user details in session
        session.setAttribute("user", user); // Store full user object in session

        return ResponseEntity.ok(user); // ✅ Return full user object in response
    }

    @GetMapping("/doctors/{doctorId}")
    public ResponseEntity<?> getDoctorById(@PathVariable String doctorId, HttpSession session) {
        User doctor = userRepository.findById(doctorId)
                .filter(user -> "DOCTOR".equals(user.getRole())) // Ensure user is a doctor
                .orElse(null);

        if (doctor == null) {
            return ResponseEntity.status(404).body("Doctor not found");
        }

        return ResponseEntity.ok(doctor); // ✅ Return full doctor data
    }



    @GetMapping("/users/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String role) {
        return ResponseEntity.ok(authService.getUsersByRole(role));
    }

    // ✅ Check session details
    @GetMapping("/session")
    public ResponseEntity<?> getSessionDetails(HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        String role = (String) session.getAttribute("role");

        if (userId == null || role == null) {
            return ResponseEntity.ok(Map.of("loggedIn", false));
        }

        return ResponseEntity.ok(Map.of("loggedIn", true, "userId", userId, "role", role));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
//        if (session != null) {
            session.invalidate(); // Invalidate the session to log the user out
//        }
        return ResponseEntity.ok("Logout successful");
    }


}
