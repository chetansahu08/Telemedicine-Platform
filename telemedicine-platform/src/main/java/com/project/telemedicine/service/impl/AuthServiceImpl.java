package com.project.telemedicine.service.impl;

import com.project.telemedicine.exception.ResourceNotFoundException;
import com.project.telemedicine.model.Admin;
import com.project.telemedicine.model.Doctor;
import com.project.telemedicine.model.Patient;
import com.project.telemedicine.model.User;
import com.project.telemedicine.repository.UserRepository;
import com.project.telemedicine.service.AuthService;

import com.project.telemedicine.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(User user) {
        // Generate a unique ID
        if (user instanceof Doctor) {
            user.setId(String.valueOf(sequenceGeneratorService.generateSequence(Doctor.SEQUENCE_NAME)));
            user.setRole("DOCTOR"); // Explicitly set the role for Doctor
        } else if (user instanceof Patient) {
            user.setId(String.valueOf(sequenceGeneratorService.generateSequence(Patient.SEQUENCE_NAME)));
            user.setRole("PATIENT"); // Explicitly set the role for Patient
        }else if (user instanceof Admin) {
            user.setId(String.valueOf(sequenceGeneratorService.generateSequence(Admin.SEQUENCE_NAME)));
            user.setRole("ADMIN"); // Set the role for Admin or other users
        }
        // Encrypt the user's password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User loginUser(String email, String rawPassword) {
        // Find user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        // Verify the password
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return user;
    }

    @Override
    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }
}
