package com.project.telemedicine.repository;

import com.project.telemedicine.model.Prescription;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrescriptionRepository extends MongoRepository<Prescription,String> {}