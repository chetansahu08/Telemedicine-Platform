package com.project.telemedicine.service;


import com.project.telemedicine.model.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> getAllPatients();
    Patient getPatientById(String id);
    Patient createPatient(Patient patient);
    Patient updatePatient(String id, Patient patient);
    void deletePatient(String id);
    Patient getPatientByEmailAndPassword(String email, String password);
}

