package com.project.telemedicine.service;

import com.project.telemedicine.model.Doctor;

import java.util.List;

public interface DoctorService {
    List<Doctor> getAllDoctors();
    Doctor getDoctorById(String id);
    Doctor createDoctor(Doctor Doctor);
    Doctor updateDoctor(String id, Doctor Doctor);
    void deleteDoctor(String id);
}

