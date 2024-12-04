package com.project.telemedicine.repository;


import com.project.telemedicine.model.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DoctorRepository extends MongoRepository<Doctor,String> {
}
