package com.project.telemedicine.repository;

import com.project.telemedicine.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatientRepository extends MongoRepository<Patient, String> {
    Patient findByEmailAndPassword(String email,String password);
}

