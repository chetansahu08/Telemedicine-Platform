package com.project.telemedicine.service;

import com.project.telemedicine.model.User;

import java.util.List;

public interface AuthService {
    User registerUser(User user);
    User loginUser(String username, String password);
    List<User> getUsersByRole(String role);
}
