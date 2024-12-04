package com.project.telemedicine.service.impl;

import com.project.telemedicine.exception.ResourceNotFoundException;
import com.project.telemedicine.model.Doctor;
import com.project.telemedicine.repository.DoctorRepository;
import com.project.telemedicine.service.DoctorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository DoctorRepository;

    @Override
    public List<Doctor> getAllDoctors() {
        return DoctorRepository.findAll();
    }

    @Override
    public Doctor getDoctorById(String id) {
        return DoctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
    }

    @Override
    public Doctor createDoctor(Doctor Doctor) {
        return DoctorRepository.save(Doctor);
    }

    @Override
    public Doctor updateDoctor(String id, Doctor Doctor) {
        Doctor existingDoctor = getDoctorById(id);
        existingDoctor.setName(Doctor.getName());
        existingDoctor.setSpecialization(Doctor.getSpecialization());
        existingDoctor.setAvailability(Doctor.getAvailability());
        return DoctorRepository.save(existingDoctor);
    }

    @Override
    public void deleteDoctor(String id) {
        Doctor existingDoctor = getDoctorById(id);
        DoctorRepository.delete(existingDoctor);
    }
}
