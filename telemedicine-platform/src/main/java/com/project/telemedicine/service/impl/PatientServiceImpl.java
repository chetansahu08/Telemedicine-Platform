package com.project.telemedicine.service.impl;

import com.project.telemedicine.exception.ResourceNotFoundException;
import com.project.telemedicine.model.Patient;
import com.project.telemedicine.repository.PatientRepository;
import com.project.telemedicine.service.PatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Patient getPatientById(String id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
    }

    @Override
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(String id, Patient patient) {
        Patient existingPatient = getPatientById(id);
        existingPatient.setName(patient.getName());
        existingPatient.setAge(patient.getAge());
        existingPatient.setEmail(patient.getEmail());
        existingPatient.setMedicalHistory(patient.getMedicalHistory());
        return patientRepository.save(existingPatient);
    }

    @Override
    public void deletePatient(String id) {
        Patient existingPatient = getPatientById(id);
        patientRepository.delete(existingPatient);
    }

    public Patient getPatientByEmailAndPassword(String email,String password){
        return patientRepository.findByEmailAndPassword(email,password);
    }
}

